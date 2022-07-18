package beatum.happy.domain.helper.utility;

import beatum.happy.domain.helper.model.Response;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.ptr.IntByReference;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Happy.He
 * @version 1.0
 * @date 7/5/2022 10:04 AM
 */
public class utils {

    /*
     * read file to bytes array
     * */
    public static byte[] readFileToBytes(File file) {
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);
        } catch (IOException ex) {
            return null;
        }
        return bytes;
    }

    /*
     * gets image from resource
     * */
    public static BufferedImage getImageFromResource(String name) {
        BufferedImage img = null;
        try {
            File imageFile = new ClassPathResource(name).getFile();
            img = ImageIO.read(imageFile);
        } catch (IOException ex) {
        }
        return img;
    }

    public static BufferedImage getImage(String filePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(utils.class.getClassLoader().getResourceAsStream(filePath));
        } catch (Exception ex) {
        }
        return img;
    }

    /**
     * Get current computer NetBIOS name.
     *
     * @return Netbios name.
     */
    public static String getComputerName() {
        char buffer[] = new char[WinBase.MAX_COMPUTERNAME_LENGTH + 1];
        IntByReference lpnSize = new IntByReference(buffer.length);
        if (!Kernel32.INSTANCE.GetComputerName(buffer, lpnSize)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return Native.toString(buffer);
    }

    /*
     * Change host name
     * */
    public static Response changeHostName(String hostName) {
        Response response = new Response();
        try {
            boolean result = MyKernel32.INSTANCE.SetComputerNameEx(5, hostName);
            response.setResult(result);
            if (result) {
                response.setMessage("Change host name[Successfully]");
            } else {
                response.setMessage("Change host name[Error]");
            }

        } catch (Exception ex) {
            response.setResult(false);
            response.setMessage("Change host name[Error]" + ex.getMessage());
        }
        return response;
    }

    /*
     * Change host name
     * */
    public static Response changeNetBIOSName(String hostName) {
        Response response = new Response();
        try {
            boolean result = MyKernel32.INSTANCE.SetComputerNameEx(4, hostName);
            response.setResult(result);
            if (result) {
                response.setMessage("Change NetBIOS name[Successfully]");
            } else {
                response.setMessage("Change NetBIOS name[Error]");
            }

        } catch (Exception ex) {
            response.setResult(false);
            response.setMessage("Change NetBIOS name[Error]" + ex.getMessage());
        }
        return response;
    }


    /*
     * Change host name
     * */
    public static Response joinDomain(String serverName, String domain, String account, String password) {
        Response response = new Response();
        try {
            int result = MyNetapi32.INSTANCE.NetJoinDomain(serverName, domain, null, account, password, (MyNetapi32.NETSETUP_JOIN_DOMAIN | MyNetapi32.NETSETUP_DOMAIN_JOIN_IF_JOINED | MyNetapi32.NETSETUP_ACCT_CREATE));
            if (result <= 0) {
                response.setMessage("Join Domain[Successfully]");
                response.setResult(true);
            } else {
                response.setMessage("Join Domain[Error]" + result);
            }

        } catch (Exception ex) {
            response.setResult(false);
            response.setMessage("Join Domain name[Error]" + ex.getMessage());
        }
        return response;
    }

    /*
     * UnjoinDomainOrWorkgroup
     * */
    public static Response NetUnjoinDomain(String account, String password) {
        Response response = new Response();
        try {
            int result = MyNetapi32.INSTANCE.NetUnjoinDomain(null, account, password, 0);
            if (result <= 0) {
                response.setMessage("Unjoin Domain [Successfully]");
                response.setResult(true);
            } else {
                response.setMessage("Unjoin Domain [Error]" + result);
            }

        } catch (Exception ex) {
            response.setResult(false);
            response.setMessage("Unjoin Domain [Error]" + ex.getMessage());
        }
        return response;
    }

    /*
     * executing windows command
     */
    public static Response execCommand(String command) {
        Response response = new Response();
        Process process = null;
        BufferedInputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            process = Runtime.getRuntime().exec(command);
            inputStream = (BufferedInputStream) process.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String temp = null;
            LinkedList<String> outputLines = new LinkedList<String>();
            while ((temp = bufferedReader.readLine()) != null) {
                outputLines.add(temp);
            }
            process.waitFor();
            response.setObject(outputLines);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setResult(false);
        } finally {
            try {
                if (null != process) {
                    process.destroy();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }

            } catch (IOException ex) {
                response.setMessage(ex.getMessage());
                response.setResult(false);
            }
        }
        return response;
    }

    /*
     * set computer name by registry
     * */
    public static Response setComputerByRegistry(String name) {
        Response response = new Response();
        Response r1 = execCommand("reg add \"HKEY_LOCAL_MACHINE\\System\\CurrentControlSet\\Control\\ComputerName\\ActiveComputerName\" /v ComputerName /t reg_sz /d " + name + " /f >nul");
        if (!r1.isResult()) {
            response.setResult(false);
            response.setMessage("Set computer by registry error [ActiveComputerName] ->" + r1.getMessage());
            response.setObject(r1.getObject());
        }


        Response r2 = execCommand("reg add \"HKEY_LOCAL_MACHINE\\System\\CurrentControlSet\\Services\\Tcpip\\Parameters\" /v \"NV Hostname\" /t reg_sz /d " + name + " /f >nul");
        if (!r2.isResult()) {
            response.setResult(false);
            response.setMessage("Set computer by registry error [Tcpip NV Hostname] ->" + r2.getMessage());
            response.setObject(r2.getObject());
        }

        Response r3 = execCommand("reg add \"HKEY_LOCAL_MACHINE\\System\\CurrentControlSet\\Services\\Tcpip\\Parameters\" /v \"Hostname\" /t reg_sz /d %" + name + "% /f >nul");
        if (!r3.isResult()) {
            response.setResult(false);
            response.setMessage("Set computer by registry error [Tcpip Hostname] ->" + r3.getMessage());
            response.setObject(r3.getObject());
        }
        response.setMessage("Set computer by registry successfully！");


        return response;
    }

    private static void printingMessage(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i);
            System.out.println(text);
        }
    }

}
