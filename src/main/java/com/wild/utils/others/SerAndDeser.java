package com.wild.utils.others;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SerAndDeser {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	private static String relativelyPath ;
	private static List<CheckCodeSer> list = new ArrayList<CheckCodeSer>();
	public static File file;
	static{
		relativelyPath = SerAndDeser.class.getClassLoader().getResource("/").getPath(); // 项目的根目录
        relativelyPath = relativelyPath.substring(1, relativelyPath.indexOf("webapps"));
        String path = relativelyPath + "webapps/checkCode";
        File fi = new File(path);
        if(!fi.exists()){
        	fi.mkdir();
        }
        System.out.println(relativelyPath);
		file = new File(path+"/checkCode.txt");
	}

	/**
	 * 序列化对象
	 */
	public static void SerializeObject(CheckCodeSer cc, boolean flag) {
		try {
			if (file.exists() && file.length() != 0) {// 如果文件存在
				FileOutputStream fo = new FileOutputStream(file, flag);
				ObjectOutputStream oo = new ObjectOutputStream(fo);
				long pos = 0;
				pos = fo.getChannel().position() - 4;
				fo.getChannel().truncate(pos);
				oo.writeObject(cc);
				fo.flush();
				oo.flush();
				oo.close();
				fo.close();
			} else {
				file.createNewFile();
				FileOutputStream fo = new FileOutputStream(file, true);
				ObjectOutputStream oo = new ObjectOutputStream(fo);
				oo.writeObject(cc);
				fo.flush();
				oo.flush();
				oo.close();
				fo.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 反序列化对象
	 * 
	 * @return
	 */
	public static CheckCodeSer DeSerializeObject(String phone) {
		ObjectInputStream oi;
		try {
			@SuppressWarnings("resource")
			FileInputStream fi = new FileInputStream(file);
			oi = new ObjectInputStream(fi);
			while (fi.available() > 0) {
				CheckCodeSer cc = (CheckCodeSer) oi.readObject();
				if (phone != null && phone.length() > 0) {
					if (phone.equalsIgnoreCase(cc.getTel())) {
						return cc;
					}
				} else {// 将所有对象存到list中
					list.add(cc);
				}
			}
			oi.close();
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否超时
	 * 
	 * @param cc：注册码对象
	 * @return
	 */
	public static boolean isTimeOut(CheckCodeSer cc) {
		long date = System.currentTimeMillis();
		String time = sdf.format(date);
		try {
			Date sdate = sdf.parse(time);
			Date edate = sdf.parse(cc.getBeginDate());
			int result = (int) (sdate.getTime() - edate.getTime());
			if (result >= 300000) {
				deleteCheckCode(cc);
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 移除已注册的验证码的信息
	 * 
	 * @param cc
	 */
	public static void deleteCheckCode(CheckCodeSer cc) {
		System.out.println("in");
		DeSerializeObject("");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTel().equalsIgnoreCase(cc.getTel())) {
				list.remove(list.get(i));
				if (list.size() == 0) {
					try {
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write("");
						bw.flush();
						bw.close();
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// SerializeObject(null,false);//重新写入
					break;
				} else {
					if (i >= 1) {
						i--;
					}
				}
			}
			SerializeObject(list.get(i), false);// 重新写入
		}
	}
}
