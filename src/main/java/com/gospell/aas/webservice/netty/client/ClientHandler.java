package com.gospell.aas.webservice.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import com.gospell.aas.common.mapper.JsonMapper;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {

	private Client client;

	public ClientHandler(Client client) {

		this.client = client;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		String s = (String) msg;
		RequestResult r = JsonMapper.getInstance().fromJson(s,
				RequestResult.class);
 

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	protected void handleAllIdle(ChannelHandlerContext ctx) {
		// System.out.println("发送心跳包");sudo ln -s /usr/local/python3/lib/libpython3.5m.so.1.0  /usr/lib/libpython3.5m.so.1.0
		 sendPingMsg(ctx);//./configure --prefix=/usr/local/python2 --enable-shared  
		 // sudo ln -s /usr/local/python2/lib/libpython2.6.so.1.0 /usr/lib/libpython2.6.so.1.0
 
	}

	protected void sendPingMsg(ChannelHandlerContext context) {
		RequestResult req = new RequestResult();
		req.setClientId("0000");
		req.setCommandType("100");
		req.setContent("心跳信息");
		req.setStatus("001");
		context.writeAndFlush(JsonMapper.toJsonString(req) + "\n");

	}

	protected void handleReaderIdle(ChannelHandlerContext ctx) {
		// System.err.println("---READER_IDLE---");
	}

	protected void handleWriterIdle(ChannelHandlerContext ctx) {
		// System.err.println("---WRITER_IDLE---");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			switch (e.state()) {
			case READER_IDLE:
				handleReaderIdle(ctx);
				break;
			case WRITER_IDLE:
				handleWriterIdle(ctx);
				break;
			case ALL_IDLE:
				handleAllIdle(ctx);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		client.doConnect();
	}

}