package com.jayqqaa12.reader.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.jayqqaa12.abase.kit.common.L;


/***
 * ge tui
 * @author 12
 *
 */
public class PushReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		Log.i("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION))
		{
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");

			if (payload != null)
			{
				String data = new String(payload);
				Log.i("GetuiSdkDemo", "Got Payload:" + data);
				//TODO anything
				

			}
			break;
		case PushConsts.GET_CLIENTID:
			
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			L.i("reader get clientid ="+cid);

			break;
		case PushConsts.THIRDPART_FEEDBACK:
//			String appid = bundle.getString("appid");
//			String taskid = bundle.getString("taskid");
//			String actionid = bundle.getString("actionid");
//			String result = bundle.getString("result");
//			long timestamp = bundle.getLong("timestamp");
			break;
		}
	}
}
