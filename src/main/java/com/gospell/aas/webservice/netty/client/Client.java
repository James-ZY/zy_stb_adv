package com.gospell.aas.webservice.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.dto.push.PushAdComboDTO;
import com.gospell.aas.dto.push.PushAdelementDTO;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.webservice.netty.dto.AdComboDeleteDTO;
import com.gospell.aas.webservice.netty.dto.AdvDeleteDTO;
import com.gospell.aas.webservice.netty.dto.ClientIdDTO;
import com.gospell.aas.webservice.netty.dto.CloseDownAdComboDTO;
import com.gospell.aas.webservice.netty.dto.CloseDownAdvDTO;
import com.gospell.aas.webservice.netty.dto.PushAdcomboToClientDTO;
import com.gospell.aas.webservice.netty.dto.PushSingelAdvDTO;
import com.gospell.aas.webservice.netty.util.NettyConstant;

/**
 * @author zhengdesheng
 * @version 1.0
 */
public class Client {
	private static final NioEventLoopGroup workGroup = new NioEventLoopGroup(4);
	private Channel channel;
	private Bootstrap bootstrap;

	public static Client client;

	private static final long READER_IDLE = 10;

	private static final long WRITER_IDLE = 0;

	private static final long ALL_IDLE = 10;

	public static synchronized Client getInstance() {
		if (null == client) {
			client = new Client();
			client.start();
		}
		return client;
	}

	public void start() {
		try {
			bootstrap = new Bootstrap();
			bootstrap.group(workGroup).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel)
								throws Exception {

							ChannelPipeline pipeline = socketChannel.pipeline();
							pipeline.addLast("ping", new IdleStateHandler(
									READER_IDLE, WRITER_IDLE, ALL_IDLE,
									TimeUnit.SECONDS));

							pipeline.addLast(new LineBasedFrameDecoder(
									1024 * 1024));
							pipeline.addLast("decoder", new StringDecoder(
									Charset.forName("UTF-8")));
							pipeline.addLast("encoder", new StringEncoder(
									Charset.forName("UTF-8")));
					
							//pipeline.addLast(new ProtobufEncoder());
							pipeline.addLast(new ClientHandler(Client.this));

						}
					});
			doConnect();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void startNetty(){
		 String s=System.getenv("netty_home");
			String commcand = "java -jar "+s+"\\gospell-netty-1.0.jar &";
			System.out.println(commcand);
			 try {
				 Runtime runtime = Runtime.getRuntime();
				 //执行命令    
				 Process process = runtime.exec(commcand);
	           //取得命令结果的输出流    
	            InputStream fis=process.getInputStream();    
	           //用一个读输出流类去读    
	            InputStreamReader isr=new InputStreamReader(fis);    
	           //用缓冲器读行    
	            BufferedReader br=new BufferedReader(isr);    
	            String line=null;    
	           //直到读完为止    
	           while((line=br.readLine())!=null)    
	            {    
	                System.out.println(line);    
	            }   
	           fis.close();
	           isr.close();
	           br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	protected void doConnect() {
		if (channel != null && channel.isActive()) {
			return;
		}
		String host = Global.getConfig("netty_host");
		int port = Integer.parseInt(Global.getConfig("netty_port"));
		ChannelFuture future = bootstrap.connect(host, port);
        channel = future.channel();
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture futureListener)
					throws Exception {
				if (futureListener.isSuccess()) {
					channel = futureListener.channel();
					System.out.println("Connect to server successfully!");
				} else {
//					System.out
//							.println("Failed to connect to server, try connect after 10s");
					
					futureListener.channel().eventLoop()
							.schedule(new Runnable() {
								@Override
								public void run() {
									doConnect();
								}
							}, 10, TimeUnit.SECONDS);
				}
			}
		});

	}

	/**
	 * 删除广告
	 * 
	 * @param id
	 * @param clientIdList
	 * @return
	 */
	public boolean deleteAdv(String id, int isDeleteNow,List<String> clientIdList) {

		List<ClientIdDTO> list = getClientIdDTO(clientIdList);
		RequestResult builder = new RequestResult();
		builder.setClientId(Global.getConfig("clientId"));
		if(isDeleteNow == Adelement.ADV_DELETE_NOW_YES){
			builder.setCommandType(NettyConstant.REP_DELETE_NOW_FROM_SERVER);
		}else{
			builder.setCommandType(NettyConstant.REP_DELETE_ADV__FROM_SERVER);
		}
	
		CloseDownAdvDTO closeDto = new CloseDownAdvDTO();
		AdvDeleteDTO dto = new AdvDeleteDTO();
		dto.setAdId(id);
		closeDto.setAdvDelete(dto);
		closeDto.setClientIdList(list);
		builder.setContent(JsonMapper.toJsonString(closeDto));
		builder.setStatus(NettyConstant.OK);
		if (channel != null && channel.isActive()) {
			try {
				channel.writeAndFlush(JsonMapper.toJsonString(builder) + "\n")
						.sync();
			} catch (InterruptedException e) {
			 
				return false;
			}

			return true;
		} else {
			return false;
		}

	}

	/**
	 * 推送单条广告到客户端
	 * 
	 * @param clientIdList
	 *            当前那些客户端
	 * @param advJson
	 *            广告下传的json字符串
	 * @return
	 */
	public boolean putAdv(List<String> clientIdList, PushAdelementDTO push,int status) {

		RequestResult builder = new RequestResult();
		builder.setClientId(Global.getConfig("clientId"));
		 if(status == Adelement.ADV_CLOSE_NOW_YES){
			builder.setCommandType(NettyConstant.REP_PLAY_NOW_FROM_SERVER);
		}else{
			builder.setCommandType(NettyConstant.REP_PUSH_ADV_FROM_SERVER);
		}
		List<String> id_list = Lists.newArrayList();
		id_list.add(push.getId());
		List<PushAdelementDTO> push_list = Lists.newArrayList();
		push_list.add(push);
		String pushStr = JsonMapper.toJsonString(push_list);
		List<PushSingelAdvDTO> list = Lists.newArrayList();
		for (int i = 0; i < clientIdList.size(); i++) {
			PushSingelAdvDTO dto = new PushSingelAdvDTO();
			dto.setClientId(clientIdList.get(i));
			dto.setPush(pushStr);
			dto.setAdvIdList(id_list);
			list.add(dto);
		}

		builder.setContent(JsonMapper.toJsonString(list));
		builder.setStatus(NettyConstant.OK);

		if (channel != null && channel.isActive()) {
			try {
				channel.writeAndFlush(JsonMapper.toJsonString(builder) + "\n")
						.sync();
			} catch (InterruptedException e) {
		 
				return false;
			}

			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 推送单条默认开机画面广告到客户端
	 * 
	 * @param clientIdList
	 *            当前那些客户端
	 * @param advJson
	 *            广告下传的json字符串
	 * @return
	 */
	public boolean putDefaultAdv(List<String> clientIdList, PushAdelementDTO push,int status) {
		RequestResult builder = new RequestResult();
		builder.setClientId(Global.getConfig("clientId"));
		builder.setCommandType(NettyConstant.REP_PUSH_DEFAULTADV_FROM_SERVER);

		List<String> id_list = Lists.newArrayList();
		id_list.add(push.getId());
		List<PushAdelementDTO> push_list = Lists.newArrayList();
		push_list.add(push);
		String pushStr = JsonMapper.toJsonString(push_list);
		List<PushSingelAdvDTO> list = Lists.newArrayList();
		for (int i = 0; i < clientIdList.size(); i++) {
			PushSingelAdvDTO dto = new PushSingelAdvDTO();
			dto.setClientId(clientIdList.get(i));
			dto.setPush(pushStr);
			dto.setAdvIdList(id_list);
			list.add(dto);
		}

		builder.setContent(JsonMapper.toJsonString(list));
		builder.setStatus(NettyConstant.OK);

		if (channel != null && channel.isActive()) {
			try {
				channel.writeAndFlush(JsonMapper.toJsonString(builder) + "\n")
						.sync();
			} catch (InterruptedException e) {
		 
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除套餐
	 * 
	 * @param id
	 * @param clientIdList
	 * @return
	 */
	public boolean deleteAdCombo(String id, List<String> clientIdList) {
		RequestResult builder = new RequestResult();
		builder.setClientId(Global.getConfig("clientId"));
		builder.setCommandType(NettyConstant.REP_DELETE_ADCOMBO_FROM_SERVER);
		List<CloseDownAdComboDTO> result_list = Lists.newArrayList();

		List<AdComboDeleteDTO> delete_list = Lists.newArrayList();
		AdComboDeleteDTO d = new AdComboDeleteDTO();
		d.setComboId(id);
		delete_list.add(d);
		for (int i = 0; i < clientIdList.size(); i++) {
			CloseDownAdComboDTO close = new CloseDownAdComboDTO();
			close.setClientId(clientIdList.get(i));
			close.setAdcomboIdList(delete_list);
			result_list.add(close);
		}	 
		builder.setContent(JsonMapper.toJsonString(result_list));
		builder.setStatus(NettyConstant.OK);
		if (channel != null && channel.isActive()) {
			try {
				channel.writeAndFlush(JsonMapper.toJsonString(builder) + "\n")
						.sync();
			} catch (InterruptedException e) {
			 
				return false;
			}

			return true;
		} else {
			return false;
		}

	}

	/**
	 * 推送单条套餐到客户端
	 * 
	 * @param clientIdList
	 *            当前那些客户端
	 * @param advJson
	 *            广告下传的json字符串
	 * @return
	 */
	public boolean putAdcombo(List<String> clientIdList, PushAdComboDTO push) {

		RequestResult builder = new RequestResult();
		builder.setClientId(Global.getConfig("clientId"));
		builder.setCommandType(NettyConstant.REP_PUSH_ADCOMBO_FROM_SERVER);
		List<String> id_list = Lists.newArrayList();
		id_list.add(push.getId());
	
		List<PushAdComboDTO> push_list = Lists.newArrayList();
		push_list.add(push);
		String pushStr = JsonMapper.toJsonString(push_list);
		List<PushAdcomboToClientDTO> list = Lists.newArrayList();
		for (int i = 0; i < clientIdList.size(); i++) {
			PushAdcomboToClientDTO dto = new PushAdcomboToClientDTO();
			dto.setClientId(clientIdList.get(i));
			dto.setPushList(pushStr);
			dto.setComboIdList(id_list);
			list.add(dto);
		}

		builder.setContent(JsonMapper.toJsonString(list));
		builder.setStatus(NettyConstant.OK);

		if (channel != null && channel.isActive()) {
			try {
				channel.writeAndFlush(JsonMapper.toJsonString(builder) + "\n")
						.sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public List<ClientIdDTO> getClientIdDTO(List<String> clientIdList) {
		List<ClientIdDTO> list = Lists.newArrayList();
		for (int i = 0; i < clientIdList.size(); i++) {
			ClientIdDTO clientId = new ClientIdDTO();
			clientId.setClientId(clientIdList.get(i));
			list.add(clientId);
		}
		return list;
	}

}
