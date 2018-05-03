package com.fise.utils.enums;

/**
 * 事件类型
 * @author tany
 *
 */

public enum EventTypeEnums {
	
    EVENT_KEY_CURRENT_INFO        (0x00,"实时信息上送事件"),
    EVENT_KEY_CROSS_SAFE_AREA     (0x01,"离开安全区域"),
    EVENT_KEY_ENTER_SAFE_AREA     (0x02,"进入安全区域"),
    EVENT_KEY_LOW_BATTERY         (0x03,"低电量"),
    EVENT_KEY_BEGIN_CHARGING      (0x04,"开始充电"),
    EVENT_KEY_END_CHARGING        (0x05,"结束充电"),
    EVENT_KEY_SOS                 (0x06,"SOS"),
    EVENT_KEY_CALL_OUT            (0x07,"主叫"),
    EVENT_KEY_CALL_IN             (0x08,"被叫"),
    EVENT_KEY_SHUTDOWN            (0x09,"关机"),
    EVENT_KEY_REPORT_BILL         (0x0a,"话费上报"),
    EVENT_KEY_DROP_DOWN           (0x0b,"设备脱落"),
    EVENT_KEY_VERSION_UPDATE_DONE (0x0c,"设备升级完成"),
    EVENT_KEY_VERSION_UPDATE      (0x0d,"升级版本"),
    EVENT_KEY_BEGIN_VIDEO         (0x0e,"开始视频"),
    EVENT_KEY_END_VIDEO           (0x0f,"结束视频"),
    EVENT_KEY_REPORT_STEP         (0x10,"运动计步"),
    EVENT_KEY_WEAR_ON             (0x11,"穿戴设备"),
    EVENT_KEY_REPLAY_VIDEO        (0x12,"精彩回放"),
    EVENT_KEY_POWER_SUPPLY        (0x13,"供电报警"),
    EVENT_KEY_DOWNLOAD            (0x14,"下载视频"),
    EVENT_KEY_MOVE                (0x15,"移动报警"),
    EVENT_KEY_NOTIFICATION        (0x16,"转通知类型消息"),
    EVENT_KEY_SET_DEFENSE         (0x17,"设防"),
    EVENT_KEY_RESET_DEFENSE       (0x18,"撤防"),
    EVENT_KEY_SHUTOFF             (0x19,"断电断油"),
    EVENT_KEY_TRAFFIC_COND        (0x1a,"车况信息"),
    EVENT_KEY_BUTTON_START        (0x1b,"启动"),
    EVENT_KEY_BUTTON_LOCK         (0x1c,"上锁"),
    EVENT_KEY_SEARCH_CAR          (0x1d,"寻车"),
    EVENT_KEY_COMMAND             (0x20,"指令事件"),
    EVENT_KEY_REPORT_CALL_LOG     (0x21,"上报通话记录"),
    EVENT_KEY_HEALTH_REPORT       (0x22,"健康数据");
	
	private Integer status;
	private String text;

	private EventTypeEnums(Integer status, String text) {
		this.status = status;
		this.text = text;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getText() {
		return this.text;
	}
	
	public static String getNameByNo(int no){
		for (EventTypeEnums item : EventTypeEnums.values()) {
			if(item.getStatus().intValue() == no){
				return item.getText();
			}
		}
		return "";
	}

}
