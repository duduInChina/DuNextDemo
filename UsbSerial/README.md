采用开源工具读取usb
https://github.com/mik3y/usb-serial-for-android/tree/master

同时工具也结合了官方API
https://developer.android.google.cn/develop/connectivity/usb/host?hl=zh-cn

考虑到项目维护，个性化修改，防止与领康sdk冲突（lksdk中包含该库）
当前基于com.github.mik3y:usb-serial-for-android:3.8.0，导入到项目

何声明指定 USB 设备的相应资源文件
<intent-filter>
    <action android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED" />
</intent-filter>
<meta-data
    android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED"
    android:resource="@xml/device_filter" />
