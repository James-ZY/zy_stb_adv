 package com.gospell.aas.common.utils.adv;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

/**
 * 销售流水号生成工具
 * @author Administrator
 *
 */
public class PrimaryGenerater  {

	private static PrimaryGenerater primaryGenerater = null;

	private PrimaryGenerater() {
	}

	/**
	 * 取得PrimaryGenerater的单例实现
	 * 
	 * @return
	 */
	public static PrimaryGenerater getInstance() {
		if (primaryGenerater == null) {
			synchronized (PrimaryGenerater.class) {
				if (primaryGenerater == null) {
					primaryGenerater = new PrimaryGenerater();
				}
			}
		}
		return primaryGenerater;
	}

	/**
	 * 生成下一个编号
	 */
	public synchronized String generaterNextNumber() {
		Date date = new Date();
		DecimalFormat df = new DecimalFormat("0");
		Random rand = new Random();
		int randNum = rand.nextInt(9000)+1000;
		return date.getTime() + df.format(randNum);
	}
}