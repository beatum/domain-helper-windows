package beatum.happy.domain.helper.utility;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/1/2022 9:09 AM
 */
public interface MyKernel32 extends StdCallLibrary {
    MyKernel32 INSTANCE = (MyKernel32) Native.load("kernel32", MyKernel32.class, W32APIOptions.DEFAULT_OPTIONS);

    /*
    * Sets a new NetBIOS or DNS name for the local computer. Name changes made by SetComputerNameEx do not take
    * effect until the user restarts the computer.
    * */
    boolean SetComputerNameEx (int NameType , String lpBuffer);
}
