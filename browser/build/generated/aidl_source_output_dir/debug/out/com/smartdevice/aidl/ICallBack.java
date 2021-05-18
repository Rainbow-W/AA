/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.smartdevice.aidl;
public interface ICallBack extends android.os.IInterface
{
  /** Default implementation for ICallBack. */
  public static class Default implements com.smartdevice.aidl.ICallBack
  {
    /**
    	 *接收回调消息
    	 */
    @Override public void onReturnValue(byte[] data, int size) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.smartdevice.aidl.ICallBack
  {
    private static final java.lang.String DESCRIPTOR = "com.smartdevice.aidl.ICallBack";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.smartdevice.aidl.ICallBack interface,
     * generating a proxy if needed.
     */
    public static com.smartdevice.aidl.ICallBack asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.smartdevice.aidl.ICallBack))) {
        return ((com.smartdevice.aidl.ICallBack)iin);
      }
      return new com.smartdevice.aidl.ICallBack.Stub.Proxy(obj);
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
        case TRANSACTION_onReturnValue:
        {
          data.enforceInterface(descriptor);
          byte[] _arg0;
          _arg0 = data.createByteArray();
          int _arg1;
          _arg1 = data.readInt();
          this.onReturnValue(_arg0, _arg1);
          reply.writeNoException();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.smartdevice.aidl.ICallBack
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
      	 *接收回调消息
      	 */
      @Override public void onReturnValue(byte[] data, int size) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeByteArray(data);
          _data.writeInt(size);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onReturnValue, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().onReturnValue(data, size);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      public static com.smartdevice.aidl.ICallBack sDefaultImpl;
    }
    static final int TRANSACTION_onReturnValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    public static boolean setDefaultImpl(com.smartdevice.aidl.ICallBack impl) {
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
    public static com.smartdevice.aidl.ICallBack getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
  	 *接收回调消息
  	 */
  public void onReturnValue(byte[] data, int size) throws android.os.RemoteException;
}
