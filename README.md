##常用的一些工具类

	Android6.0权限申请：	动态申请权限
	GZipUtils：	具备压缩功能的输出流、解压功能的输入流
	MyLogger：	打印log的工具
	MD5Utils:	MD5加密
	SPUtils:	sharedprefreences工具类（获取String、Int、Boolean类型的值，万能的put方法）
	UtilsOpenFile：	用来打开各种格式文件的工具类
	DownloadUtils：	下载工具类（获取当前激活的网络信息、获取网络类型、万能的文件下载工具、下载完成时,回调）
	CommonUtils：	公共工具类（耗时操作放到线程池里面执行、创建公用的Handler、把任务放到主线程中执行、在任意线程里面打印Toast、dp==>px、sp==>px、浮点数的估值、颜色的估值、优先显示昵称、获取当前的时间、计算状态栏的高度）
	CommonViewHolder：	listview的viewholder工具类
	time:			时间转换工具类
	Album-master：	相册多选 而且还不用担心6.0的权限问题
	jni开发笔记：	as的jni用法
	ExamplesFromMyBlog-master：	侧滑加沉浸式，在5.x上也能适配沉浸式
	
	安卓屏幕适配像素转换工具：
		http://labs.rampinteractive.co.uk/android_dp_px_calculator/
		
	使用方法：
		http://blog.csdn.net/hello_1s/article/details/52604693
		http://blog.csdn.net/wawxf2008/article/details/47379577 
		
	安卓屏幕适配之dimens适配：
		UI切图给的单位是px，px不适用于安卓，因为机型多，分辨率不一样，采用dimens，生成不同的dimens文件，这样就可以直接写UI给的px了，系统会直接找到对		 应分辨率的dimens文件。
