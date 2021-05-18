/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.smartdevice.aidl;
public interface IZKCService extends android.os.IInterface
{
  /** Default implementation for IZKCService. */
  public static class Default implements com.smartdevice.aidl.IZKCService
  {
    /**
    	* 设置当前功能模块
    	* @param cb:	实现ICallback接口的回调
    	*/
    @Override public boolean setModuleFlag(int module) throws android.os.RemoteException
    {
      return false;
    }
    /************************************************************
    	 *                                                        ***
    	 *                 打印机                                 ***
    	 *                                                        ***
    	 ************************************************************//**
    	* 注册
    	* @param cb:	实现ICallback接口的回调
    	*/
    @Override public void registerCallBack(java.lang.String flag, com.smartdevice.aidl.ICallBack callback) throws android.os.RemoteException
    {
    }
    /**
    	* 注销回调
    	* @param cb:	实现ICallback接口的回调
    	*/
    @Override public void unregisterCallBack(java.lang.String flag, com.smartdevice.aidl.ICallBack callback) throws android.os.RemoteException
    {
    }
    /**
    	* 停止回调任务
    	*/
    @Override public void stopRunningTask() throws android.os.RemoteException
    {
    }
    /**
    	* 判断回调任务是否正在运行
    	*/
    @Override public boolean isTaskRunning() throws android.os.RemoteException
    {
      return false;
    }
    /**
    	* 获取打印机固件版本
    	*/
    @Override public void getFirmwareVersion() throws android.os.RemoteException
    {
    }
    /**
    	* 获取ZKCService服务版本
    	*/
    @Override public java.lang.String getServiceVersion() throws android.os.RemoteException
    {
      return null;
    }
    /**
    	* 获取ZKCService服务版本
    	*/
    @Override public int getDeviceModel() throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	 * 初始化打印机
    	 */
    @Override public void printerInit() throws android.os.RemoteException
    {
    }
    /**
    	* 获取打印机状态
    	*/
    @Override public java.lang.String getPrinterStatus() throws android.os.RemoteException
    {
      return null;
    }
    /**
    	* 打印机自检，打印机会打印自检页
    	*/
    @Override public void printerSelfChecking() throws android.os.RemoteException
    {
    }
    /**
    	* 检查打印机是否空闲
    	*/
    @Override public boolean checkPrinterAvailable() throws android.os.RemoteException
    {
      return false;
    }
    /**
    	* 使用原始指令打印
    	* @param data 指令
    	* @param callback  结果回调
    	*/
    @Override public void sendRAWData(java.lang.String flag, byte[] data) throws android.os.RemoteException
    {
    }
    /**
    	* 设置对齐模式，对之后打印有影响，除非初始化
    	* @param alignment:	对齐方式 0--居左, 1--居中, 2--居右
    	* @param callback  结果回调
    	*/
    @Override public void setAlignment(int alignment) throws android.os.RemoteException
    {
    }
    /**
    	* 设置打印字体, 对之后打印有影响，除非初始化
    	* (目前只支持两种字体)
    	* @param typeface:	字体类型 0--ASCII(12*24) 汉字（24*24），1--ASCII(8*16) 汉字（16*16）
    	*/
    @Override public void setTypeface(int type) throws android.os.RemoteException
    {
    }
    /**
    	* 设置字体大小, 对之后打印有影响，除非初始化
    	* 调整字体大小会影响字符宽度，每行字符数量也会随之改变，
    	* @param fontsize:	字体大小类型 0--字符正常： 不放大，1--字符 2 倍高：纵向放大，
    	*                                2--字符 2 倍宽：横向放大，3--字符 2 倍整体放大
    	*/
    @Override public void setFontSize(int type) throws android.os.RemoteException
    {
    }
    /**
    	* 打印文字，文字宽度满一行自动换行排版，不满一整行不打印除非强制换行
    	* @param text:	要打印的文字字符串
    	*/
    @Override public void printGBKText(java.lang.String text) throws android.os.RemoteException
    {
    }
    /**
    	* 打印文字，文字宽度满一行自动换行排版，不满一整行不打印除非强制换行
    	* @param text:	要打印的文字字符串
    	*/
    @Override public void printUnicodeText(java.lang.String text) throws android.os.RemoteException
    {
    }
    /**
    	* 打印指定字体的文本，字体设置只对本次有效
    	* @param text:			要打印文字
    	* @param typeface:		字体类型
    	* @param fontsize:		字体大小	类型
    	*/
    @Override public void printTextWithFont(java.lang.String text, int type, int size) throws android.os.RemoteException
    {
    }
    /**
    	* 打印指定字体,字号、对齐方式的文本，字体设置只对本次有效
    	* @param text:			要打印文字
    	* @param typeface:		字体类型
    	* @param fontsize:		字体大小	类型
    	* @param alginStyle:	对齐方式(0居左, 1居中, 2居右)
    	*/
    @Override public void printTextAlgin(java.lang.String text, int type, int size, int alginStyle) throws android.os.RemoteException
    {
    }
    /**
    	* 打印表格的一行，可以指定列宽、对齐方式
    	* @param colsTextArr   各列文本字符串数组
    	* @param colsWidthArr  各列宽度数组(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0)
    	* @param colsAlign	        各列对齐方式(0居左, 1居中, 2居右)
    	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本换行
    	*/
    @Override public void printColumnsText(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign) throws android.os.RemoteException
    {
    }
    /**
    	* 打印图片
    	* @param bitmap: 	图片bitmap对象
    	*/
    @Override public void printBitmap(android.graphics.Bitmap bitmap) throws android.os.RemoteException
    {
    }
    /**
    	* 打印图片（含对齐方式）
    	* @param bitmap: 	图片bitmap对象
    	* @parm position:   图片位置 0--居左，1--居中，2--居右
    	*/
    @Override public void printBitmapAlgin(android.graphics.Bitmap bitmap, int width, int height, int position) throws android.os.RemoteException
    {
    }
    /**
    	* 创建一维条码图片
    	* @param data: 		条码数据
    	* @param symbology: 	条码类型
    	*    0 -- UPC-A，
    	*    1 -- UPC-E，
    	*    2 -- JAN13(EAN13)，
    	*    3 -- JAN8(EAN8)，
    	*    4 -- CODE39，
    	*    5 -- ITF，
    	*    6 -- CODABAR，
    	*    7 -- CODE93，
    	*    8 -- CODE128
    	* @param height: 		条码高度, 取值1到255, 默认162
    	* @param width: 		条码宽度, 取值0至5, 默认3
    	* @param displayText:	是否显示文字
    	*/
    @Override public android.graphics.Bitmap createBarCode(java.lang.String data, int codeFormat, int width, int height, boolean displayText) throws android.os.RemoteException
    {
      return null;
    }
    /**
    	* 创建二维条码图片
    	* @param data:			二维码数据
    	* @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
    	*/
    @Override public android.graphics.Bitmap createQRCode(java.lang.String data, int width, int height) throws android.os.RemoteException
    {
      return null;
    }
    /********************************
    	********添加日期 2017/03/14******
    	*********************************//*
    	*打印不干胶获取间隙
    	*/
    @Override public void generateSpace() throws android.os.RemoteException
    {
    }
    /********************************
    	********添加日期 2017/04/25******
    	*********************************//*
    	*打印图片（灰度）
    	*/
    @Override public boolean printImageGray(android.graphics.Bitmap bitmap) throws android.os.RemoteException
    {
      return false;
    }
    /*
    	*打印图片（光栅）
    	*/
    @Override public boolean printRasterImage(android.graphics.Bitmap bitmap) throws android.os.RemoteException
    {
      return false;
    }
    /*
    	*打印UNINCODE（光栅）
    	*/
    @Override public boolean printUnicode_1F30(java.lang.String textStr) throws android.os.RemoteException
    {
      return false;
    }
    /*
    	*设置打印语言
    	*/
    @Override public void setPrintLanguage(java.lang.String language) throws android.os.RemoteException
    {
    }
    /*
    	*获取固件版本
    	*/
    @Override public java.lang.String getFirmwareVersion1() throws android.os.RemoteException
    {
      return null;
    }
    /*
    	*获取固件版本
    	*/
    @Override public java.lang.String getFirmwareVersion2() throws android.os.RemoteException
    {
      return null;
    }
    /*
    	*指定打印条码
    	*/
    @Override public boolean printBarCode(java.lang.String content) throws android.os.RemoteException
    {
      return false;
    }
    /*
    	*指定打印二维码
    	*/
    @Override public boolean printQrCode(java.lang.String content) throws android.os.RemoteException
    {
      return false;
    }
    /************************************************************
    	 *                                                        ***
    	 *                 客显屏                                 ***
    	 *                                                        ***
    	 ************************************************************//**
    	 * 控制屏背光电源
    	 * @param btFlg 控制背光亮度 0===》亮；1===》不亮
    	 */
    @Override public void openBackLight(int btFlg) throws android.os.RemoteException
    {
    }
    /**
    	 *  显示彩色图片，图片格式不限
    	 * @param bitmapSrc 显示图片位图
    	 */
    @Override public boolean showRGB565Image(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException
    {
      return false;
    }
    /**
    	 *  显示彩色图片，图片格式不限
    	 * @param path 图片路径
    	 */
    @Override public boolean showRGB565ImageByPath(java.lang.String path) throws android.os.RemoteException
    {
      return false;
    }
    /**
    	 * 指定位置显示彩色图片，图片格式不限
    	 * @param bitmapSrc
    	 *            图片
    	 * @param x
    	 *            起点X坐标
    	 * @param y
    	 *            起点Y坐标
    	 * @param width
    	 *            显示宽度
    	 * @param height
    	 *            显示高度
    	 * @return
    	 */
    @Override public boolean showRGB565ImageLocation(android.graphics.Bitmap bitmapSrc, int x, int y, int width, int height) throws android.os.RemoteException
    {
      return false;
    }
    /**
    	 * 更新屏幕LOGO
    	 * @param bitmapSrc
    	 * @return
    	 */
    @Override public boolean updateLogo(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException
    {
      return false;
    }
    /**
    	 * 更新屏幕LOGO
    	 * @param path
    	 * @return
    	 */
    @Override public boolean updateLogoByPath(java.lang.String path) throws android.os.RemoteException
    {
      return false;
    }
    /**
    	 * 显示双色图片
    	 *
    	 * @param BackColor
    	 * @param ForeColor
    	 * @param bitmapSrc
    	 * @return
    	 */
    @Override public boolean showDotImage(int BackColor, int ForeColor, android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException
    {
      return false;
    }
    /**
    	 * 屏幕中心显示彩色图片
    	 * @param bitmapSrc
    	 * @return
    	 */
    @Override public boolean showRGB565ImageCenter(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException
    {
      return false;
    }
    /************************************************************
    	 *                                                        ***
    	 *                	PSAM卡                                ***
    	 *                                                        ***
    	 ************************************************************//**
    	* 打开PSAM电源
    	*/
    @Override public int Open() throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	* 关闭PSAM电源
    	*/
    @Override public int Close(long fd) throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	* 打开/关闭GPIO口
    	* @return  true--success; false--fail
    	*/
    @Override public boolean setGPIO(int io, int status) throws android.os.RemoteException
    {
      return false;
    }
    /**
    	* 打开指定PSAM卡
    	* @param carPositin: PSAM卡位置
    	* @return 返回0成功
    	*/
    @Override public int openCard(int carPositin) throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	 * 打开设备2
    	 *
    	 * @param 卡槽编号
    	 */
    @Override public int openCard2(int[] fd, int slotno) throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	 * 打开设备3
    	 *
    	 * @param 卡槽编号
    	 */
    @Override public int openCard3(long fd, int slotno) throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	* 关闭打开的PSAM卡
    	* @return 返回0成功
    	*/
    @Override public int CloseCard() throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	 *功能：	关闭设备2
    	 *c参数 v2 true:设备3/设备2
    	 *参数： [in]unsigned long fd传入要关闭的设备句柄
    	 *返回值：正确为0，错误为非0
    	*/
    @Override public int CloseCard2(long fd, boolean v2) throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	* 重置当前打开的PSAM卡
    	* @param power: 指定的电压大小
    	* @return 以字节数组返回PSAM卡号
    	*/
    @Override public byte[] ResetCard(int power) throws android.os.RemoteException
    {
      return null;
    }
    /**
    	 *功能：	设备复位2
    	 *参数： [in]unsigned long fd传入要关闭的设备句柄
    	 *       [out]unsigned char *atr 传出设备复位信息
    	 *       [in/out]int *atrLen 传出设备复位信息长度
    	 *返回值：正确为0，错误为非0
    	*/
    @Override public int ResetCard2(long fd, byte[] atr, int[] atrLen) throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	 *功能：	设备复位3
    	 *参数： [in]unsigned long fd传入要关闭的设备句柄
    	 *       [out]unsigned char *atr 传出设备复位信息
    	 *       [in/out]int *atrLen 传出设备复位信息长度
    	 *返回值：正确为0，错误为非0
    	*/
    @Override public byte[] ResetCard3(long fd, int slotno, int pw) throws android.os.RemoteException
    {
      return null;
    }
    /**
    	* 给当前PSAM卡发送APDU命令
    	* @param apdu: APDU命令
    	* @return 以字节数组返回命令结果
    	*/
    @Override public byte[] CardApdu(byte[] apdu) throws android.os.RemoteException
    {
      return null;
    }
    /**
    	 *功能：	发送APDU指令2
    	 *参数： [in]unsigned long fd传入设备句柄
    	 *     [in]unsigned char *apdu 要发送的apdu指令
    	 *     [in]int apduLength 要发送的apdu指令长度
    	 *     [out]unsigned char*response 返回数据内容
    	 *     [in/out]int* respLength 返回数据长度
    	 *返回值：正确为0，错误为非0
    	 *注意：此接口不执行自动取响应数据（即此接口不自动发送“00c0”这种取响应指令）
    	*/
    @Override public int CardApdu2(long fd, byte[] apdu, int apduLength, byte[] response, int[] respLength) throws android.os.RemoteException
    {
      return 0;
    }
    /**
    	 *功能：	发送APDU指令3
    	 *参数： [in]unsigned long fd传入设备句柄
    	 *     [in]unsigned char *apdu 要发送的apdu指令
    	 *     [in]int apduLength 要发送的apdu指令长度
    	 *     [out]unsigned char*response 返回数据内容
    	 *     [in/out]int* respLength 返回数据长度
    	 *返回值：正确为0，错误为非0
    	 *注意：此接口不执行自动取响应数据（即此接口不自动发送“00c0”这种取响应指令）
    	*/
    @Override public byte[] CardApdu3(long fd, byte[] apdu, int apduLength) throws android.os.RemoteException
    {
      return null;
    }
    /************************************************************
    	 *                                                        ***
    	 *                	扫描                                  ***
    	 *                                                        ***
    	 ************************************************************//**
    	* 打开扫描
    	* @param status: true:打开；false:关闭
    	*/
    @Override public void openScan(boolean status) throws android.os.RemoteException
    {
    }
    /**
    	* 扫描
    	*/
    @Override public void scan() throws android.os.RemoteException
    {
    }
    /**
    	* 数据末尾追加回车
    	* @param status: true:追加；false:不追加
    	*/
    @Override public void dataAppendEnter(boolean status) throws android.os.RemoteException
    {
    }
    /**
    	* 扫描成功提示音
    	* @param status:  true:需要；false:不需要
    	*/
    @Override public void appendRingTone(boolean status) throws android.os.RemoteException
    {
    }
    /**
    	* 连续扫描
    	* @param status: true:需要；false:不需要
    	*/
    @Override public void continueScan(boolean status) throws android.os.RemoteException
    {
    }
    /**
    	* 扫码重复提示
    	* @param status: true:提示；false:不提示
    	*/
    @Override public void scanRepeatHint(boolean status) throws android.os.RemoteException
    {
    }
    /**
    	* 恢复出厂设置
    	* @param status: true:设置生效；false:设置不生效
    	*/
    @Override public void recoveryFactorySet(boolean status) throws android.os.RemoteException
    {
    }
    /**
    	* 发送扫描指令
    	* @param byte[] 发送的指令数据
    	* @return 接收的结果
    	*/
    @Override public byte[] sendCommand(byte[] buffer) throws android.os.RemoteException
    {
      return null;
    }
    /**
    	* 是否可以正常扫描
    	* @return true:扫描已打开；false:扫描已关闭；
    	*/
    @Override public boolean isScanning() throws android.os.RemoteException
    {
      return false;
    }
    /**
    	* 获取身份证信息
    	* @return 身份证信息
    	*/
    @Override public java.lang.String getIdentifyInfo() throws android.os.RemoteException
    {
      return null;
    }
    /**
    	* 打开电源
    	* @return 身份证信息
    	*/
    @Override public boolean turnOn() throws android.os.RemoteException
    {
      return false;
    }
    /**
    	* 断开电源
    	* @return 身份证信息
    	*/
    @Override public boolean turnOff() throws android.os.RemoteException
    {
      return false;
    }
    /**
    	* 断开电源
    	* @return 身份证信息
    	*/
    @Override public android.graphics.Bitmap getHeader() throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.smartdevice.aidl.IZKCService
  {
    private static final java.lang.String DESCRIPTOR = "com.smartdevice.aidl.IZKCService";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.smartdevice.aidl.IZKCService interface,
     * generating a proxy if needed.
     */
    public static com.smartdevice.aidl.IZKCService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.smartdevice.aidl.IZKCService))) {
        return ((com.smartdevice.aidl.IZKCService)iin);
      }
      return new com.smartdevice.aidl.IZKCService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_setModuleFlag:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          boolean _result = this.setModuleFlag(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_registerCallBack:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.smartdevice.aidl.ICallBack _arg1;
          _arg1 = com.smartdevice.aidl.ICallBack.Stub.asInterface(data.readStrongBinder());
          this.registerCallBack(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_unregisterCallBack:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          com.smartdevice.aidl.ICallBack _arg1;
          _arg1 = com.smartdevice.aidl.ICallBack.Stub.asInterface(data.readStrongBinder());
          this.unregisterCallBack(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_stopRunningTask:
        {
          data.enforceInterface(descriptor);
          this.stopRunningTask();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_isTaskRunning:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isTaskRunning();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getFirmwareVersion:
        {
          data.enforceInterface(descriptor);
          this.getFirmwareVersion();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_getServiceVersion:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getServiceVersion();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getDeviceModel:
        {
          data.enforceInterface(descriptor);
          int _result = this.getDeviceModel();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_printerInit:
        {
          data.enforceInterface(descriptor);
          this.printerInit();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_getPrinterStatus:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getPrinterStatus();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_printerSelfChecking:
        {
          data.enforceInterface(descriptor);
          this.printerSelfChecking();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_checkPrinterAvailable:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.checkPrinterAvailable();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_sendRAWData:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          byte[] _arg1;
          _arg1 = data.createByteArray();
          this.sendRAWData(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setAlignment:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          this.setAlignment(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setTypeface:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          this.setTypeface(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setFontSize:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          this.setFontSize(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printGBKText:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.printGBKText(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printUnicodeText:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.printUnicodeText(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printTextWithFont:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          this.printTextWithFont(_arg0, _arg1, _arg2);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printTextAlgin:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          this.printTextAlgin(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printColumnsText:
        {
          data.enforceInterface(descriptor);
          java.lang.String[] _arg0;
          _arg0 = data.createStringArray();
          int[] _arg1;
          _arg1 = data.createIntArray();
          int[] _arg2;
          _arg2 = data.createIntArray();
          this.printColumnsText(_arg0, _arg1, _arg2);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printBitmap:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          this.printBitmap(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printBitmapAlgin:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          this.printBitmapAlgin(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_createBarCode:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          boolean _arg4;
          _arg4 = (0!=data.readInt());
          android.graphics.Bitmap _result = this.createBarCode(_arg0, _arg1, _arg2, _arg3, _arg4);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_createQRCode:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          android.graphics.Bitmap _result = this.createQRCode(_arg0, _arg1, _arg2);
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_generateSpace:
        {
          data.enforceInterface(descriptor);
          this.generateSpace();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_printImageGray:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          boolean _result = this.printImageGray(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_printRasterImage:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          boolean _result = this.printRasterImage(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_printUnicode_1F30:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.printUnicode_1F30(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_setPrintLanguage:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.setPrintLanguage(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_getFirmwareVersion1:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getFirmwareVersion1();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getFirmwareVersion2:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getFirmwareVersion2();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_printBarCode:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.printBarCode(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_printQrCode:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.printQrCode(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_openBackLight:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          this.openBackLight(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_showRGB565Image:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          boolean _result = this.showRGB565Image(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_showRGB565ImageByPath:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.showRGB565ImageByPath(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_showRGB565ImageLocation:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          int _arg4;
          _arg4 = data.readInt();
          boolean _result = this.showRGB565ImageLocation(_arg0, _arg1, _arg2, _arg3, _arg4);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_updateLogo:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          boolean _result = this.updateLogo(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_updateLogoByPath:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.updateLogoByPath(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_showDotImage:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          int _arg1;
          _arg1 = data.readInt();
          android.graphics.Bitmap _arg2;
          if ((0!=data.readInt())) {
            _arg2 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg2 = null;
          }
          boolean _result = this.showDotImage(_arg0, _arg1, _arg2);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_showRGB565ImageCenter:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _arg0;
          if ((0!=data.readInt())) {
            _arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          boolean _result = this.showRGB565ImageCenter(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_Open:
        {
          data.enforceInterface(descriptor);
          int _result = this.Open();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_Close:
        {
          data.enforceInterface(descriptor);
          long _arg0;
          _arg0 = data.readLong();
          int _result = this.Close(_arg0);
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_setGPIO:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          int _arg1;
          _arg1 = data.readInt();
          boolean _result = this.setGPIO(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_openCard:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          int _result = this.openCard(_arg0);
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_openCard2:
        {
          data.enforceInterface(descriptor);
          int[] _arg0;
          _arg0 = data.createIntArray();
          int _arg1;
          _arg1 = data.readInt();
          int _result = this.openCard2(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(_result);
          reply.writeIntArray(_arg0);
          return true;
        }
        case TRANSACTION_openCard3:
        {
          data.enforceInterface(descriptor);
          long _arg0;
          _arg0 = data.readLong();
          int _arg1;
          _arg1 = data.readInt();
          int _result = this.openCard3(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_CloseCard:
        {
          data.enforceInterface(descriptor);
          int _result = this.CloseCard();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_CloseCard2:
        {
          data.enforceInterface(descriptor);
          long _arg0;
          _arg0 = data.readLong();
          boolean _arg1;
          _arg1 = (0!=data.readInt());
          int _result = this.CloseCard2(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_ResetCard:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          byte[] _result = this.ResetCard(_arg0);
          reply.writeNoException();
          reply.writeByteArray(_result);
          return true;
        }
        case TRANSACTION_ResetCard2:
        {
          data.enforceInterface(descriptor);
          long _arg0;
          _arg0 = data.readLong();
          byte[] _arg1;
          _arg1 = data.createByteArray();
          int[] _arg2;
          _arg2 = data.createIntArray();
          int _result = this.ResetCard2(_arg0, _arg1, _arg2);
          reply.writeNoException();
          reply.writeInt(_result);
          reply.writeByteArray(_arg1);
          reply.writeIntArray(_arg2);
          return true;
        }
        case TRANSACTION_ResetCard3:
        {
          data.enforceInterface(descriptor);
          long _arg0;
          _arg0 = data.readLong();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          byte[] _result = this.ResetCard3(_arg0, _arg1, _arg2);
          reply.writeNoException();
          reply.writeByteArray(_result);
          return true;
        }
        case TRANSACTION_CardApdu:
        {
          data.enforceInterface(descriptor);
          byte[] _arg0;
          _arg0 = data.createByteArray();
          byte[] _result = this.CardApdu(_arg0);
          reply.writeNoException();
          reply.writeByteArray(_result);
          return true;
        }
        case TRANSACTION_CardApdu2:
        {
          data.enforceInterface(descriptor);
          long _arg0;
          _arg0 = data.readLong();
          byte[] _arg1;
          _arg1 = data.createByteArray();
          int _arg2;
          _arg2 = data.readInt();
          byte[] _arg3;
          _arg3 = data.createByteArray();
          int[] _arg4;
          _arg4 = data.createIntArray();
          int _result = this.CardApdu2(_arg0, _arg1, _arg2, _arg3, _arg4);
          reply.writeNoException();
          reply.writeInt(_result);
          reply.writeByteArray(_arg3);
          reply.writeIntArray(_arg4);
          return true;
        }
        case TRANSACTION_CardApdu3:
        {
          data.enforceInterface(descriptor);
          long _arg0;
          _arg0 = data.readLong();
          byte[] _arg1;
          _arg1 = data.createByteArray();
          int _arg2;
          _arg2 = data.readInt();
          byte[] _result = this.CardApdu3(_arg0, _arg1, _arg2);
          reply.writeNoException();
          reply.writeByteArray(_result);
          return true;
        }
        case TRANSACTION_openScan:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.openScan(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_scan:
        {
          data.enforceInterface(descriptor);
          this.scan();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_dataAppendEnter:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.dataAppendEnter(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_appendRingTone:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.appendRingTone(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_continueScan:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.continueScan(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_scanRepeatHint:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.scanRepeatHint(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_recoveryFactorySet:
        {
          data.enforceInterface(descriptor);
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.recoveryFactorySet(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_sendCommand:
        {
          data.enforceInterface(descriptor);
          byte[] _arg0;
          _arg0 = data.createByteArray();
          byte[] _result = this.sendCommand(_arg0);
          reply.writeNoException();
          reply.writeByteArray(_result);
          return true;
        }
        case TRANSACTION_isScanning:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isScanning();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getIdentifyInfo:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getIdentifyInfo();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_turnOn:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.turnOn();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_turnOff:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.turnOff();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getHeader:
        {
          data.enforceInterface(descriptor);
          android.graphics.Bitmap _result = this.getHeader();
          reply.writeNoException();
          if ((_result!=null)) {
            reply.writeInt(1);
            _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.smartdevice.aidl.IZKCService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
      	* 设置当前功能模块
      	* @param cb:	实现ICallback接口的回调
      	*/
      @Override public boolean setModuleFlag(int module) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(module);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setModuleFlag, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setModuleFlag(module);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /************************************************************
      	 *                                                        ***
      	 *                 打印机                                 ***
      	 *                                                        ***
      	 ************************************************************//**
      	* 注册
      	* @param cb:	实现ICallback接口的回调
      	*/
      @Override public void registerCallBack(java.lang.String flag, com.smartdevice.aidl.ICallBack callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(flag);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_registerCallBack, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().registerCallBack(flag, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 注销回调
      	* @param cb:	实现ICallback接口的回调
      	*/
      @Override public void unregisterCallBack(java.lang.String flag, com.smartdevice.aidl.ICallBack callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(flag);
          _data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_unregisterCallBack, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().unregisterCallBack(flag, callback);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 停止回调任务
      	*/
      @Override public void stopRunningTask() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_stopRunningTask, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().stopRunningTask();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 判断回调任务是否正在运行
      	*/
      @Override public boolean isTaskRunning() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isTaskRunning, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isTaskRunning();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 获取打印机固件版本
      	*/
      @Override public void getFirmwareVersion() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getFirmwareVersion, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().getFirmwareVersion();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 获取ZKCService服务版本
      	*/
      @Override public java.lang.String getServiceVersion() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getServiceVersion, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getServiceVersion();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 获取ZKCService服务版本
      	*/
      @Override public int getDeviceModel() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getDeviceModel, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getDeviceModel();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 * 初始化打印机
      	 */
      @Override public void printerInit() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printerInit, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printerInit();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 获取打印机状态
      	*/
      @Override public java.lang.String getPrinterStatus() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPrinterStatus, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPrinterStatus();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 打印机自检，打印机会打印自检页
      	*/
      @Override public void printerSelfChecking() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printerSelfChecking, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printerSelfChecking();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 检查打印机是否空闲
      	*/
      @Override public boolean checkPrinterAvailable() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_checkPrinterAvailable, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().checkPrinterAvailable();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 使用原始指令打印
      	* @param data 指令
      	* @param callback  结果回调
      	*/
      @Override public void sendRAWData(java.lang.String flag, byte[] data) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(flag);
          _data.writeByteArray(data);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendRAWData, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().sendRAWData(flag, data);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 设置对齐模式，对之后打印有影响，除非初始化
      	* @param alignment:	对齐方式 0--居左, 1--居中, 2--居右
      	* @param callback  结果回调
      	*/
      @Override public void setAlignment(int alignment) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(alignment);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setAlignment, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setAlignment(alignment);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 设置打印字体, 对之后打印有影响，除非初始化
      	* (目前只支持两种字体)
      	* @param typeface:	字体类型 0--ASCII(12*24) 汉字（24*24），1--ASCII(8*16) 汉字（16*16）
      	*/
      @Override public void setTypeface(int type) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(type);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setTypeface, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setTypeface(type);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 设置字体大小, 对之后打印有影响，除非初始化
      	* 调整字体大小会影响字符宽度，每行字符数量也会随之改变，
      	* @param fontsize:	字体大小类型 0--字符正常： 不放大，1--字符 2 倍高：纵向放大，
      	*                                2--字符 2 倍宽：横向放大，3--字符 2 倍整体放大
      	*/
      @Override public void setFontSize(int type) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(type);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setFontSize, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setFontSize(type);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 打印文字，文字宽度满一行自动换行排版，不满一整行不打印除非强制换行
      	* @param text:	要打印的文字字符串
      	*/
      @Override public void printGBKText(java.lang.String text) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(text);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printGBKText, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printGBKText(text);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 打印文字，文字宽度满一行自动换行排版，不满一整行不打印除非强制换行
      	* @param text:	要打印的文字字符串
      	*/
      @Override public void printUnicodeText(java.lang.String text) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(text);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printUnicodeText, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printUnicodeText(text);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 打印指定字体的文本，字体设置只对本次有效
      	* @param text:			要打印文字
      	* @param typeface:		字体类型
      	* @param fontsize:		字体大小	类型
      	*/
      @Override public void printTextWithFont(java.lang.String text, int type, int size) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(text);
          _data.writeInt(type);
          _data.writeInt(size);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printTextWithFont, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printTextWithFont(text, type, size);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 打印指定字体,字号、对齐方式的文本，字体设置只对本次有效
      	* @param text:			要打印文字
      	* @param typeface:		字体类型
      	* @param fontsize:		字体大小	类型
      	* @param alginStyle:	对齐方式(0居左, 1居中, 2居右)
      	*/
      @Override public void printTextAlgin(java.lang.String text, int type, int size, int alginStyle) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(text);
          _data.writeInt(type);
          _data.writeInt(size);
          _data.writeInt(alginStyle);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printTextAlgin, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printTextAlgin(text, type, size, alginStyle);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 打印表格的一行，可以指定列宽、对齐方式
      	* @param colsTextArr   各列文本字符串数组
      	* @param colsWidthArr  各列宽度数组(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0)
      	* @param colsAlign	        各列对齐方式(0居左, 1居中, 2居右)
      	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本换行
      	*/
      @Override public void printColumnsText(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStringArray(colsTextArr);
          _data.writeIntArray(colsWidthArr);
          _data.writeIntArray(colsAlign);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printColumnsText, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printColumnsText(colsTextArr, colsWidthArr, colsAlign);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 打印图片
      	* @param bitmap: 	图片bitmap对象
      	*/
      @Override public void printBitmap(android.graphics.Bitmap bitmap) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmap!=null)) {
            _data.writeInt(1);
            bitmap.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_printBitmap, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printBitmap(bitmap);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 打印图片（含对齐方式）
      	* @param bitmap: 	图片bitmap对象
      	* @parm position:   图片位置 0--居左，1--居中，2--居右
      	*/
      @Override public void printBitmapAlgin(android.graphics.Bitmap bitmap, int width, int height, int position) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmap!=null)) {
            _data.writeInt(1);
            bitmap.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeInt(width);
          _data.writeInt(height);
          _data.writeInt(position);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printBitmapAlgin, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().printBitmapAlgin(bitmap, width, height, position);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 创建一维条码图片
      	* @param data: 		条码数据
      	* @param symbology: 	条码类型
      	*    0 -- UPC-A，
      	*    1 -- UPC-E，
      	*    2 -- JAN13(EAN13)，
      	*    3 -- JAN8(EAN8)，
      	*    4 -- CODE39，
      	*    5 -- ITF，
      	*    6 -- CODABAR，
      	*    7 -- CODE93，
      	*    8 -- CODE128
      	* @param height: 		条码高度, 取值1到255, 默认162
      	* @param width: 		条码宽度, 取值0至5, 默认3
      	* @param displayText:	是否显示文字
      	*/
      @Override public android.graphics.Bitmap createBarCode(java.lang.String data, int codeFormat, int width, int height, boolean displayText) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        android.graphics.Bitmap _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(data);
          _data.writeInt(codeFormat);
          _data.writeInt(width);
          _data.writeInt(height);
          _data.writeInt(((displayText)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_createBarCode, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().createBarCode(data, codeFormat, width, height, displayText);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = android.graphics.Bitmap.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 创建二维条码图片
      	* @param data:			二维码数据
      	* @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
      	*/
      @Override public android.graphics.Bitmap createQRCode(java.lang.String data, int width, int height) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        android.graphics.Bitmap _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(data);
          _data.writeInt(width);
          _data.writeInt(height);
          boolean _status = mRemote.transact(Stub.TRANSACTION_createQRCode, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().createQRCode(data, width, height);
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = android.graphics.Bitmap.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /********************************
      	********添加日期 2017/03/14******
      	*********************************//*
      	*打印不干胶获取间隙
      	*/
      @Override public void generateSpace() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_generateSpace, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().generateSpace();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /********************************
      	********添加日期 2017/04/25******
      	*********************************//*
      	*打印图片（灰度）
      	*/
      @Override public boolean printImageGray(android.graphics.Bitmap bitmap) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmap!=null)) {
            _data.writeInt(1);
            bitmap.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_printImageGray, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().printImageGray(bitmap);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /*
      	*打印图片（光栅）
      	*/
      @Override public boolean printRasterImage(android.graphics.Bitmap bitmap) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmap!=null)) {
            _data.writeInt(1);
            bitmap.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_printRasterImage, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().printRasterImage(bitmap);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /*
      	*打印UNINCODE（光栅）
      	*/
      @Override public boolean printUnicode_1F30(java.lang.String textStr) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(textStr);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printUnicode_1F30, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().printUnicode_1F30(textStr);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /*
      	*设置打印语言
      	*/
      @Override public void setPrintLanguage(java.lang.String language) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(language);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setPrintLanguage, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setPrintLanguage(language);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /*
      	*获取固件版本
      	*/
      @Override public java.lang.String getFirmwareVersion1() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getFirmwareVersion1, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getFirmwareVersion1();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /*
      	*获取固件版本
      	*/
      @Override public java.lang.String getFirmwareVersion2() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getFirmwareVersion2, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getFirmwareVersion2();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /*
      	*指定打印条码
      	*/
      @Override public boolean printBarCode(java.lang.String content) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(content);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printBarCode, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().printBarCode(content);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /*
      	*指定打印二维码
      	*/
      @Override public boolean printQrCode(java.lang.String content) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(content);
          boolean _status = mRemote.transact(Stub.TRANSACTION_printQrCode, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().printQrCode(content);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /************************************************************
      	 *                                                        ***
      	 *                 客显屏                                 ***
      	 *                                                        ***
      	 ************************************************************//**
      	 * 控制屏背光电源
      	 * @param btFlg 控制背光亮度 0===》亮；1===》不亮
      	 */
      @Override public void openBackLight(int btFlg) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(btFlg);
          boolean _status = mRemote.transact(Stub.TRANSACTION_openBackLight, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().openBackLight(btFlg);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	 *  显示彩色图片，图片格式不限
      	 * @param bitmapSrc 显示图片位图
      	 */
      @Override public boolean showRGB565Image(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmapSrc!=null)) {
            _data.writeInt(1);
            bitmapSrc.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_showRGB565Image, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().showRGB565Image(bitmapSrc);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 *  显示彩色图片，图片格式不限
      	 * @param path 图片路径
      	 */
      @Override public boolean showRGB565ImageByPath(java.lang.String path) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(path);
          boolean _status = mRemote.transact(Stub.TRANSACTION_showRGB565ImageByPath, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().showRGB565ImageByPath(path);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 * 指定位置显示彩色图片，图片格式不限
      	 * @param bitmapSrc
      	 *            图片
      	 * @param x
      	 *            起点X坐标
      	 * @param y
      	 *            起点Y坐标
      	 * @param width
      	 *            显示宽度
      	 * @param height
      	 *            显示高度
      	 * @return
      	 */
      @Override public boolean showRGB565ImageLocation(android.graphics.Bitmap bitmapSrc, int x, int y, int width, int height) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmapSrc!=null)) {
            _data.writeInt(1);
            bitmapSrc.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          _data.writeInt(x);
          _data.writeInt(y);
          _data.writeInt(width);
          _data.writeInt(height);
          boolean _status = mRemote.transact(Stub.TRANSACTION_showRGB565ImageLocation, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().showRGB565ImageLocation(bitmapSrc, x, y, width, height);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 * 更新屏幕LOGO
      	 * @param bitmapSrc
      	 * @return
      	 */
      @Override public boolean updateLogo(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmapSrc!=null)) {
            _data.writeInt(1);
            bitmapSrc.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_updateLogo, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().updateLogo(bitmapSrc);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 * 更新屏幕LOGO
      	 * @param path
      	 * @return
      	 */
      @Override public boolean updateLogoByPath(java.lang.String path) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(path);
          boolean _status = mRemote.transact(Stub.TRANSACTION_updateLogoByPath, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().updateLogoByPath(path);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 * 显示双色图片
      	 *
      	 * @param BackColor
      	 * @param ForeColor
      	 * @param bitmapSrc
      	 * @return
      	 */
      @Override public boolean showDotImage(int BackColor, int ForeColor, android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(BackColor);
          _data.writeInt(ForeColor);
          if ((bitmapSrc!=null)) {
            _data.writeInt(1);
            bitmapSrc.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_showDotImage, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().showDotImage(BackColor, ForeColor, bitmapSrc);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 * 屏幕中心显示彩色图片
      	 * @param bitmapSrc
      	 * @return
      	 */
      @Override public boolean showRGB565ImageCenter(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((bitmapSrc!=null)) {
            _data.writeInt(1);
            bitmapSrc.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_showRGB565ImageCenter, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().showRGB565ImageCenter(bitmapSrc);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /************************************************************
      	 *                                                        ***
      	 *                	PSAM卡                                ***
      	 *                                                        ***
      	 ************************************************************//**
      	* 打开PSAM电源
      	*/
      @Override public int Open() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_Open, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().Open();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 关闭PSAM电源
      	*/
      @Override public int Close(long fd) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeLong(fd);
          boolean _status = mRemote.transact(Stub.TRANSACTION_Close, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().Close(fd);
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 打开/关闭GPIO口
      	* @return  true--success; false--fail
      	*/
      @Override public boolean setGPIO(int io, int status) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(io);
          _data.writeInt(status);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setGPIO, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setGPIO(io, status);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 打开指定PSAM卡
      	* @param carPositin: PSAM卡位置
      	* @return 返回0成功
      	*/
      @Override public int openCard(int carPositin) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(carPositin);
          boolean _status = mRemote.transact(Stub.TRANSACTION_openCard, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().openCard(carPositin);
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 * 打开设备2
      	 *
      	 * @param 卡槽编号
      	 */
      @Override public int openCard2(int[] fd, int slotno) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeIntArray(fd);
          _data.writeInt(slotno);
          boolean _status = mRemote.transact(Stub.TRANSACTION_openCard2, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().openCard2(fd, slotno);
          }
          _reply.readException();
          _result = _reply.readInt();
          _reply.readIntArray(fd);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 * 打开设备3
      	 *
      	 * @param 卡槽编号
      	 */
      @Override public int openCard3(long fd, int slotno) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeLong(fd);
          _data.writeInt(slotno);
          boolean _status = mRemote.transact(Stub.TRANSACTION_openCard3, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().openCard3(fd, slotno);
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 关闭打开的PSAM卡
      	* @return 返回0成功
      	*/
      @Override public int CloseCard() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_CloseCard, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().CloseCard();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 *功能：	关闭设备2
      	 *c参数 v2 true:设备3/设备2
      	 *参数： [in]unsigned long fd传入要关闭的设备句柄
      	 *返回值：正确为0，错误为非0
      	*/
      @Override public int CloseCard2(long fd, boolean v2) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeLong(fd);
          _data.writeInt(((v2)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_CloseCard2, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().CloseCard2(fd, v2);
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 重置当前打开的PSAM卡
      	* @param power: 指定的电压大小
      	* @return 以字节数组返回PSAM卡号
      	*/
      @Override public byte[] ResetCard(int power) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        byte[] _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(power);
          boolean _status = mRemote.transact(Stub.TRANSACTION_ResetCard, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().ResetCard(power);
          }
          _reply.readException();
          _result = _reply.createByteArray();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 *功能：	设备复位2
      	 *参数： [in]unsigned long fd传入要关闭的设备句柄
      	 *       [out]unsigned char *atr 传出设备复位信息
      	 *       [in/out]int *atrLen 传出设备复位信息长度
      	 *返回值：正确为0，错误为非0
      	*/
      @Override public int ResetCard2(long fd, byte[] atr, int[] atrLen) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeLong(fd);
          _data.writeByteArray(atr);
          _data.writeIntArray(atrLen);
          boolean _status = mRemote.transact(Stub.TRANSACTION_ResetCard2, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().ResetCard2(fd, atr, atrLen);
          }
          _reply.readException();
          _result = _reply.readInt();
          _reply.readByteArray(atr);
          _reply.readIntArray(atrLen);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 *功能：	设备复位3
      	 *参数： [in]unsigned long fd传入要关闭的设备句柄
      	 *       [out]unsigned char *atr 传出设备复位信息
      	 *       [in/out]int *atrLen 传出设备复位信息长度
      	 *返回值：正确为0，错误为非0
      	*/
      @Override public byte[] ResetCard3(long fd, int slotno, int pw) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        byte[] _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeLong(fd);
          _data.writeInt(slotno);
          _data.writeInt(pw);
          boolean _status = mRemote.transact(Stub.TRANSACTION_ResetCard3, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().ResetCard3(fd, slotno, pw);
          }
          _reply.readException();
          _result = _reply.createByteArray();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 给当前PSAM卡发送APDU命令
      	* @param apdu: APDU命令
      	* @return 以字节数组返回命令结果
      	*/
      @Override public byte[] CardApdu(byte[] apdu) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        byte[] _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeByteArray(apdu);
          boolean _status = mRemote.transact(Stub.TRANSACTION_CardApdu, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().CardApdu(apdu);
          }
          _reply.readException();
          _result = _reply.createByteArray();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 *功能：	发送APDU指令2
      	 *参数： [in]unsigned long fd传入设备句柄
      	 *     [in]unsigned char *apdu 要发送的apdu指令
      	 *     [in]int apduLength 要发送的apdu指令长度
      	 *     [out]unsigned char*response 返回数据内容
      	 *     [in/out]int* respLength 返回数据长度
      	 *返回值：正确为0，错误为非0
      	 *注意：此接口不执行自动取响应数据（即此接口不自动发送“00c0”这种取响应指令）
      	*/
      @Override public int CardApdu2(long fd, byte[] apdu, int apduLength, byte[] response, int[] respLength) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeLong(fd);
          _data.writeByteArray(apdu);
          _data.writeInt(apduLength);
          _data.writeByteArray(response);
          _data.writeIntArray(respLength);
          boolean _status = mRemote.transact(Stub.TRANSACTION_CardApdu2, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().CardApdu2(fd, apdu, apduLength, response, respLength);
          }
          _reply.readException();
          _result = _reply.readInt();
          _reply.readByteArray(response);
          _reply.readIntArray(respLength);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	 *功能：	发送APDU指令3
      	 *参数： [in]unsigned long fd传入设备句柄
      	 *     [in]unsigned char *apdu 要发送的apdu指令
      	 *     [in]int apduLength 要发送的apdu指令长度
      	 *     [out]unsigned char*response 返回数据内容
      	 *     [in/out]int* respLength 返回数据长度
      	 *返回值：正确为0，错误为非0
      	 *注意：此接口不执行自动取响应数据（即此接口不自动发送“00c0”这种取响应指令）
      	*/
      @Override public byte[] CardApdu3(long fd, byte[] apdu, int apduLength) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        byte[] _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeLong(fd);
          _data.writeByteArray(apdu);
          _data.writeInt(apduLength);
          boolean _status = mRemote.transact(Stub.TRANSACTION_CardApdu3, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().CardApdu3(fd, apdu, apduLength);
          }
          _reply.readException();
          _result = _reply.createByteArray();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /************************************************************
      	 *                                                        ***
      	 *                	扫描                                  ***
      	 *                                                        ***
      	 ************************************************************//**
      	* 打开扫描
      	* @param status: true:打开；false:关闭
      	*/
      @Override public void openScan(boolean status) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((status)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_openScan, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().openScan(status);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 扫描
      	*/
      @Override public void scan() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_scan, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().scan();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 数据末尾追加回车
      	* @param status: true:追加；false:不追加
      	*/
      @Override public void dataAppendEnter(boolean status) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((status)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_dataAppendEnter, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().dataAppendEnter(status);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 扫描成功提示音
      	* @param status:  true:需要；false:不需要
      	*/
      @Override public void appendRingTone(boolean status) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((status)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_appendRingTone, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().appendRingTone(status);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 连续扫描
      	* @param status: true:需要；false:不需要
      	*/
      @Override public void continueScan(boolean status) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((status)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_continueScan, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().continueScan(status);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 扫码重复提示
      	* @param status: true:提示；false:不提示
      	*/
      @Override public void scanRepeatHint(boolean status) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((status)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_scanRepeatHint, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().scanRepeatHint(status);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 恢复出厂设置
      	* @param status: true:设置生效；false:设置不生效
      	*/
      @Override public void recoveryFactorySet(boolean status) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((status)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_recoveryFactorySet, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().recoveryFactorySet(status);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
      	* 发送扫描指令
      	* @param byte[] 发送的指令数据
      	* @return 接收的结果
      	*/
      @Override public byte[] sendCommand(byte[] buffer) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        byte[] _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeByteArray(buffer);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendCommand, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().sendCommand(buffer);
          }
          _reply.readException();
          _result = _reply.createByteArray();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 是否可以正常扫描
      	* @return true:扫描已打开；false:扫描已关闭；
      	*/
      @Override public boolean isScanning() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isScanning, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isScanning();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 获取身份证信息
      	* @return 身份证信息
      	*/
      @Override public java.lang.String getIdentifyInfo() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getIdentifyInfo, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getIdentifyInfo();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 打开电源
      	* @return 身份证信息
      	*/
      @Override public boolean turnOn() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_turnOn, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().turnOn();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 断开电源
      	* @return 身份证信息
      	*/
      @Override public boolean turnOff() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_turnOff, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().turnOff();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
      	* 断开电源
      	* @return 身份证信息
      	*/
      @Override public android.graphics.Bitmap getHeader() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        android.graphics.Bitmap _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getHeader, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getHeader();
          }
          _reply.readException();
          if ((0!=_reply.readInt())) {
            _result = android.graphics.Bitmap.CREATOR.createFromParcel(_reply);
          }
          else {
            _result = null;
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static com.smartdevice.aidl.IZKCService sDefaultImpl;
    }
    static final int TRANSACTION_setModuleFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_registerCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_unregisterCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_stopRunningTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_isTaskRunning = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_getFirmwareVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_getServiceVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_getDeviceModel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_printerInit = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_getPrinterStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_printerSelfChecking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    static final int TRANSACTION_checkPrinterAvailable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
    static final int TRANSACTION_sendRAWData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
    static final int TRANSACTION_setAlignment = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
    static final int TRANSACTION_setTypeface = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
    static final int TRANSACTION_setFontSize = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
    static final int TRANSACTION_printGBKText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
    static final int TRANSACTION_printUnicodeText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
    static final int TRANSACTION_printTextWithFont = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
    static final int TRANSACTION_printTextAlgin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
    static final int TRANSACTION_printColumnsText = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
    static final int TRANSACTION_printBitmap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
    static final int TRANSACTION_printBitmapAlgin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
    static final int TRANSACTION_createBarCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
    static final int TRANSACTION_createQRCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
    static final int TRANSACTION_generateSpace = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
    static final int TRANSACTION_printImageGray = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
    static final int TRANSACTION_printRasterImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
    static final int TRANSACTION_printUnicode_1F30 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
    static final int TRANSACTION_setPrintLanguage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
    static final int TRANSACTION_getFirmwareVersion1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
    static final int TRANSACTION_getFirmwareVersion2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
    static final int TRANSACTION_printBarCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
    static final int TRANSACTION_printQrCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
    static final int TRANSACTION_openBackLight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
    static final int TRANSACTION_showRGB565Image = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
    static final int TRANSACTION_showRGB565ImageByPath = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
    static final int TRANSACTION_showRGB565ImageLocation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
    static final int TRANSACTION_updateLogo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
    static final int TRANSACTION_updateLogoByPath = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
    static final int TRANSACTION_showDotImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
    static final int TRANSACTION_showRGB565ImageCenter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
    static final int TRANSACTION_Open = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
    static final int TRANSACTION_Close = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
    static final int TRANSACTION_setGPIO = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
    static final int TRANSACTION_openCard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
    static final int TRANSACTION_openCard2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
    static final int TRANSACTION_openCard3 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
    static final int TRANSACTION_CloseCard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 48);
    static final int TRANSACTION_CloseCard2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 49);
    static final int TRANSACTION_ResetCard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 50);
    static final int TRANSACTION_ResetCard2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 51);
    static final int TRANSACTION_ResetCard3 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 52);
    static final int TRANSACTION_CardApdu = (android.os.IBinder.FIRST_CALL_TRANSACTION + 53);
    static final int TRANSACTION_CardApdu2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 54);
    static final int TRANSACTION_CardApdu3 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 55);
    static final int TRANSACTION_openScan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 56);
    static final int TRANSACTION_scan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 57);
    static final int TRANSACTION_dataAppendEnter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 58);
    static final int TRANSACTION_appendRingTone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 59);
    static final int TRANSACTION_continueScan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 60);
    static final int TRANSACTION_scanRepeatHint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 61);
    static final int TRANSACTION_recoveryFactorySet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 62);
    static final int TRANSACTION_sendCommand = (android.os.IBinder.FIRST_CALL_TRANSACTION + 63);
    static final int TRANSACTION_isScanning = (android.os.IBinder.FIRST_CALL_TRANSACTION + 64);
    static final int TRANSACTION_getIdentifyInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 65);
    static final int TRANSACTION_turnOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 66);
    static final int TRANSACTION_turnOff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 67);
    static final int TRANSACTION_getHeader = (android.os.IBinder.FIRST_CALL_TRANSACTION + 68);
    public static boolean setDefaultImpl(com.smartdevice.aidl.IZKCService impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.smartdevice.aidl.IZKCService getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
  	* 设置当前功能模块
  	* @param cb:	实现ICallback接口的回调
  	*/
  public boolean setModuleFlag(int module) throws android.os.RemoteException;
  /************************************************************
  	 *                                                        ***
  	 *                 打印机                                 ***
  	 *                                                        ***
  	 ************************************************************//**
  	* 注册
  	* @param cb:	实现ICallback接口的回调
  	*/
  public void registerCallBack(java.lang.String flag, com.smartdevice.aidl.ICallBack callback) throws android.os.RemoteException;
  /**
  	* 注销回调
  	* @param cb:	实现ICallback接口的回调
  	*/
  public void unregisterCallBack(java.lang.String flag, com.smartdevice.aidl.ICallBack callback) throws android.os.RemoteException;
  /**
  	* 停止回调任务
  	*/
  public void stopRunningTask() throws android.os.RemoteException;
  /**
  	* 判断回调任务是否正在运行
  	*/
  public boolean isTaskRunning() throws android.os.RemoteException;
  /**
  	* 获取打印机固件版本
  	*/
  public void getFirmwareVersion() throws android.os.RemoteException;
  /**
  	* 获取ZKCService服务版本
  	*/
  public java.lang.String getServiceVersion() throws android.os.RemoteException;
  /**
  	* 获取ZKCService服务版本
  	*/
  public int getDeviceModel() throws android.os.RemoteException;
  /**
  	 * 初始化打印机
  	 */
  public void printerInit() throws android.os.RemoteException;
  /**
  	* 获取打印机状态
  	*/
  public java.lang.String getPrinterStatus() throws android.os.RemoteException;
  /**
  	* 打印机自检，打印机会打印自检页
  	*/
  public void printerSelfChecking() throws android.os.RemoteException;
  /**
  	* 检查打印机是否空闲
  	*/
  public boolean checkPrinterAvailable() throws android.os.RemoteException;
  /**
  	* 使用原始指令打印
  	* @param data 指令
  	* @param callback  结果回调
  	*/
  public void sendRAWData(java.lang.String flag, byte[] data) throws android.os.RemoteException;
  /**
  	* 设置对齐模式，对之后打印有影响，除非初始化
  	* @param alignment:	对齐方式 0--居左, 1--居中, 2--居右
  	* @param callback  结果回调
  	*/
  public void setAlignment(int alignment) throws android.os.RemoteException;
  /**
  	* 设置打印字体, 对之后打印有影响，除非初始化
  	* (目前只支持两种字体)
  	* @param typeface:	字体类型 0--ASCII(12*24) 汉字（24*24），1--ASCII(8*16) 汉字（16*16）
  	*/
  public void setTypeface(int type) throws android.os.RemoteException;
  /**
  	* 设置字体大小, 对之后打印有影响，除非初始化
  	* 调整字体大小会影响字符宽度，每行字符数量也会随之改变，
  	* @param fontsize:	字体大小类型 0--字符正常： 不放大，1--字符 2 倍高：纵向放大，
  	*                                2--字符 2 倍宽：横向放大，3--字符 2 倍整体放大
  	*/
  public void setFontSize(int type) throws android.os.RemoteException;
  /**
  	* 打印文字，文字宽度满一行自动换行排版，不满一整行不打印除非强制换行
  	* @param text:	要打印的文字字符串
  	*/
  public void printGBKText(java.lang.String text) throws android.os.RemoteException;
  /**
  	* 打印文字，文字宽度满一行自动换行排版，不满一整行不打印除非强制换行
  	* @param text:	要打印的文字字符串
  	*/
  public void printUnicodeText(java.lang.String text) throws android.os.RemoteException;
  /**
  	* 打印指定字体的文本，字体设置只对本次有效
  	* @param text:			要打印文字
  	* @param typeface:		字体类型
  	* @param fontsize:		字体大小	类型
  	*/
  public void printTextWithFont(java.lang.String text, int type, int size) throws android.os.RemoteException;
  /**
  	* 打印指定字体,字号、对齐方式的文本，字体设置只对本次有效
  	* @param text:			要打印文字
  	* @param typeface:		字体类型
  	* @param fontsize:		字体大小	类型
  	* @param alginStyle:	对齐方式(0居左, 1居中, 2居右)
  	*/
  public void printTextAlgin(java.lang.String text, int type, int size, int alginStyle) throws android.os.RemoteException;
  /**
  	* 打印表格的一行，可以指定列宽、对齐方式
  	* @param colsTextArr   各列文本字符串数组
  	* @param colsWidthArr  各列宽度数组(以英文字符计算, 每个中文字符占两个英文字符, 每个宽度大于0)
  	* @param colsAlign	        各列对齐方式(0居左, 1居中, 2居右)
  	* 备注: 三个参数的数组长度应该一致, 如果colsText[i]的宽度大于colsWidth[i], 则文本换行
  	*/
  public void printColumnsText(java.lang.String[] colsTextArr, int[] colsWidthArr, int[] colsAlign) throws android.os.RemoteException;
  /**
  	* 打印图片
  	* @param bitmap: 	图片bitmap对象
  	*/
  public void printBitmap(android.graphics.Bitmap bitmap) throws android.os.RemoteException;
  /**
  	* 打印图片（含对齐方式）
  	* @param bitmap: 	图片bitmap对象
  	* @parm position:   图片位置 0--居左，1--居中，2--居右
  	*/
  public void printBitmapAlgin(android.graphics.Bitmap bitmap, int width, int height, int position) throws android.os.RemoteException;
  /**
  	* 创建一维条码图片
  	* @param data: 		条码数据
  	* @param symbology: 	条码类型
  	*    0 -- UPC-A，
  	*    1 -- UPC-E，
  	*    2 -- JAN13(EAN13)，
  	*    3 -- JAN8(EAN8)，
  	*    4 -- CODE39，
  	*    5 -- ITF，
  	*    6 -- CODABAR，
  	*    7 -- CODE93，
  	*    8 -- CODE128
  	* @param height: 		条码高度, 取值1到255, 默认162
  	* @param width: 		条码宽度, 取值0至5, 默认3
  	* @param displayText:	是否显示文字
  	*/
  public android.graphics.Bitmap createBarCode(java.lang.String data, int codeFormat, int width, int height, boolean displayText) throws android.os.RemoteException;
  /**
  	* 创建二维条码图片
  	* @param data:			二维码数据
  	* @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
  	*/
  public android.graphics.Bitmap createQRCode(java.lang.String data, int width, int height) throws android.os.RemoteException;
  /********************************
  	********添加日期 2017/03/14******
  	*********************************//*
  	*打印不干胶获取间隙
  	*/
  public void generateSpace() throws android.os.RemoteException;
  /********************************
  	********添加日期 2017/04/25******
  	*********************************//*
  	*打印图片（灰度）
  	*/
  public boolean printImageGray(android.graphics.Bitmap bitmap) throws android.os.RemoteException;
  /*
  	*打印图片（光栅）
  	*/
  public boolean printRasterImage(android.graphics.Bitmap bitmap) throws android.os.RemoteException;
  /*
  	*打印UNINCODE（光栅）
  	*/
  public boolean printUnicode_1F30(java.lang.String textStr) throws android.os.RemoteException;
  /*
  	*设置打印语言
  	*/
  public void setPrintLanguage(java.lang.String language) throws android.os.RemoteException;
  /*
  	*获取固件版本
  	*/
  public java.lang.String getFirmwareVersion1() throws android.os.RemoteException;
  /*
  	*获取固件版本
  	*/
  public java.lang.String getFirmwareVersion2() throws android.os.RemoteException;
  /*
  	*指定打印条码
  	*/
  public boolean printBarCode(java.lang.String content) throws android.os.RemoteException;
  /*
  	*指定打印二维码
  	*/
  public boolean printQrCode(java.lang.String content) throws android.os.RemoteException;
  /************************************************************
  	 *                                                        ***
  	 *                 客显屏                                 ***
  	 *                                                        ***
  	 ************************************************************//**
  	 * 控制屏背光电源
  	 * @param btFlg 控制背光亮度 0===》亮；1===》不亮
  	 */
  public void openBackLight(int btFlg) throws android.os.RemoteException;
  /**
  	 *  显示彩色图片，图片格式不限
  	 * @param bitmapSrc 显示图片位图
  	 */
  public boolean showRGB565Image(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException;
  /**
  	 *  显示彩色图片，图片格式不限
  	 * @param path 图片路径
  	 */
  public boolean showRGB565ImageByPath(java.lang.String path) throws android.os.RemoteException;
  /**
  	 * 指定位置显示彩色图片，图片格式不限
  	 * @param bitmapSrc
  	 *            图片
  	 * @param x
  	 *            起点X坐标
  	 * @param y
  	 *            起点Y坐标
  	 * @param width
  	 *            显示宽度
  	 * @param height
  	 *            显示高度
  	 * @return
  	 */
  public boolean showRGB565ImageLocation(android.graphics.Bitmap bitmapSrc, int x, int y, int width, int height) throws android.os.RemoteException;
  /**
  	 * 更新屏幕LOGO
  	 * @param bitmapSrc
  	 * @return
  	 */
  public boolean updateLogo(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException;
  /**
  	 * 更新屏幕LOGO
  	 * @param path
  	 * @return
  	 */
  public boolean updateLogoByPath(java.lang.String path) throws android.os.RemoteException;
  /**
  	 * 显示双色图片
  	 *
  	 * @param BackColor
  	 * @param ForeColor
  	 * @param bitmapSrc
  	 * @return
  	 */
  public boolean showDotImage(int BackColor, int ForeColor, android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException;
  /**
  	 * 屏幕中心显示彩色图片
  	 * @param bitmapSrc
  	 * @return
  	 */
  public boolean showRGB565ImageCenter(android.graphics.Bitmap bitmapSrc) throws android.os.RemoteException;
  /************************************************************
  	 *                                                        ***
  	 *                	PSAM卡                                ***
  	 *                                                        ***
  	 ************************************************************//**
  	* 打开PSAM电源
  	*/
  public int Open() throws android.os.RemoteException;
  /**
  	* 关闭PSAM电源
  	*/
  public int Close(long fd) throws android.os.RemoteException;
  /**
  	* 打开/关闭GPIO口
  	* @return  true--success; false--fail
  	*/
  public boolean setGPIO(int io, int status) throws android.os.RemoteException;
  /**
  	* 打开指定PSAM卡
  	* @param carPositin: PSAM卡位置
  	* @return 返回0成功
  	*/
  public int openCard(int carPositin) throws android.os.RemoteException;
  /**
  	 * 打开设备2
  	 *
  	 * @param 卡槽编号
  	 */
  public int openCard2(int[] fd, int slotno) throws android.os.RemoteException;
  /**
  	 * 打开设备3
  	 *
  	 * @param 卡槽编号
  	 */
  public int openCard3(long fd, int slotno) throws android.os.RemoteException;
  /**
  	* 关闭打开的PSAM卡
  	* @return 返回0成功
  	*/
  public int CloseCard() throws android.os.RemoteException;
  /**
  	 *功能：	关闭设备2
  	 *c参数 v2 true:设备3/设备2
  	 *参数： [in]unsigned long fd传入要关闭的设备句柄
  	 *返回值：正确为0，错误为非0
  	*/
  public int CloseCard2(long fd, boolean v2) throws android.os.RemoteException;
  /**
  	* 重置当前打开的PSAM卡
  	* @param power: 指定的电压大小
  	* @return 以字节数组返回PSAM卡号
  	*/
  public byte[] ResetCard(int power) throws android.os.RemoteException;
  /**
  	 *功能：	设备复位2
  	 *参数： [in]unsigned long fd传入要关闭的设备句柄
  	 *       [out]unsigned char *atr 传出设备复位信息
  	 *       [in/out]int *atrLen 传出设备复位信息长度
  	 *返回值：正确为0，错误为非0
  	*/
  public int ResetCard2(long fd, byte[] atr, int[] atrLen) throws android.os.RemoteException;
  /**
  	 *功能：	设备复位3
  	 *参数： [in]unsigned long fd传入要关闭的设备句柄
  	 *       [out]unsigned char *atr 传出设备复位信息
  	 *       [in/out]int *atrLen 传出设备复位信息长度
  	 *返回值：正确为0，错误为非0
  	*/
  public byte[] ResetCard3(long fd, int slotno, int pw) throws android.os.RemoteException;
  /**
  	* 给当前PSAM卡发送APDU命令
  	* @param apdu: APDU命令
  	* @return 以字节数组返回命令结果
  	*/
  public byte[] CardApdu(byte[] apdu) throws android.os.RemoteException;
  /**
  	 *功能：	发送APDU指令2
  	 *参数： [in]unsigned long fd传入设备句柄
  	 *     [in]unsigned char *apdu 要发送的apdu指令
  	 *     [in]int apduLength 要发送的apdu指令长度
  	 *     [out]unsigned char*response 返回数据内容
  	 *     [in/out]int* respLength 返回数据长度
  	 *返回值：正确为0，错误为非0
  	 *注意：此接口不执行自动取响应数据（即此接口不自动发送“00c0”这种取响应指令）
  	*/
  public int CardApdu2(long fd, byte[] apdu, int apduLength, byte[] response, int[] respLength) throws android.os.RemoteException;
  /**
  	 *功能：	发送APDU指令3
  	 *参数： [in]unsigned long fd传入设备句柄
  	 *     [in]unsigned char *apdu 要发送的apdu指令
  	 *     [in]int apduLength 要发送的apdu指令长度
  	 *     [out]unsigned char*response 返回数据内容
  	 *     [in/out]int* respLength 返回数据长度
  	 *返回值：正确为0，错误为非0
  	 *注意：此接口不执行自动取响应数据（即此接口不自动发送“00c0”这种取响应指令）
  	*/
  public byte[] CardApdu3(long fd, byte[] apdu, int apduLength) throws android.os.RemoteException;
  /************************************************************
  	 *                                                        ***
  	 *                	扫描                                  ***
  	 *                                                        ***
  	 ************************************************************//**
  	* 打开扫描
  	* @param status: true:打开；false:关闭
  	*/
  public void openScan(boolean status) throws android.os.RemoteException;
  /**
  	* 扫描
  	*/
  public void scan() throws android.os.RemoteException;
  /**
  	* 数据末尾追加回车
  	* @param status: true:追加；false:不追加
  	*/
  public void dataAppendEnter(boolean status) throws android.os.RemoteException;
  /**
  	* 扫描成功提示音
  	* @param status:  true:需要；false:不需要
  	*/
  public void appendRingTone(boolean status) throws android.os.RemoteException;
  /**
  	* 连续扫描
  	* @param status: true:需要；false:不需要
  	*/
  public void continueScan(boolean status) throws android.os.RemoteException;
  /**
  	* 扫码重复提示
  	* @param status: true:提示；false:不提示
  	*/
  public void scanRepeatHint(boolean status) throws android.os.RemoteException;
  /**
  	* 恢复出厂设置
  	* @param status: true:设置生效；false:设置不生效
  	*/
  public void recoveryFactorySet(boolean status) throws android.os.RemoteException;
  /**
  	* 发送扫描指令
  	* @param byte[] 发送的指令数据
  	* @return 接收的结果
  	*/
  public byte[] sendCommand(byte[] buffer) throws android.os.RemoteException;
  /**
  	* 是否可以正常扫描
  	* @return true:扫描已打开；false:扫描已关闭；
  	*/
  public boolean isScanning() throws android.os.RemoteException;
  /**
  	* 获取身份证信息
  	* @return 身份证信息
  	*/
  public java.lang.String getIdentifyInfo() throws android.os.RemoteException;
  /**
  	* 打开电源
  	* @return 身份证信息
  	*/
  public boolean turnOn() throws android.os.RemoteException;
  /**
  	* 断开电源
  	* @return 身份证信息
  	*/
  public boolean turnOff() throws android.os.RemoteException;
  /**
  	* 断开电源
  	* @return 身份证信息
  	*/
  public android.graphics.Bitmap getHeader() throws android.os.RemoteException;
}
