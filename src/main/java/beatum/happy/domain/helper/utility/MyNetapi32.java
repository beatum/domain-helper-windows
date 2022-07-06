package beatum.happy.domain.helper.utility;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.win32.W32APITypeMapper;

/**
 * @author Happy.He
 * @version 1.0
 * @date 6/23/2022 12:54 PM
 */
public interface MyNetapi32 extends StdCallLibrary {

    MyNetapi32 INSTANCE = (MyNetapi32) Native.load("Netapi32", MyNetapi32.class, W32APIOptions.DEFAULT_OPTIONS);
    int MAX_PREFERRED_LENGTH = -1;

    public static int NETSETUP_JOIN_DOMAIN = 0x00000001;
    public static int NETSETUP_ACCT_CREATE = 0x00000002;
    public static int NETSETUP_ACCT_DELETE = 0x00000004;
    public static int NETSETUP_WIN9X_UPGRADE = 0x00000010;
    public static int NETSETUP_DOMAIN_JOIN_IF_JOINED = 0x00000020;
    public static int NETSETUP_JOIN_UNSECURE = 0x00000040;
    public static int NETSETUP_MACHINE_PWD_PASSED = 0x00000080;
    public static int NETSETUP_DEFER_SPN_SET = 0x10000000;

    /**
     * Contains information about the session, including name of the computer; name
     * of the user; and active and idle times for the session.
     */
    @Structure.FieldOrder({"sesi10_cname", "sesi10_username", "sesi10_time", "sesi10_idle_time"})
    class SESSION_INFO_10 extends Structure {
        public String sesi10_cname;
        public String sesi10_username;
        public int sesi10_time;
        public int sesi10_idle_time;

        public SESSION_INFO_10() {
            super(W32APITypeMapper.UNICODE);
        }

        public SESSION_INFO_10(Pointer p) {
            super(p, Structure.ALIGN_DEFAULT, W32APITypeMapper.UNICODE);
            read();
        }
    }

    /*
     * Net join domain
     * */
    public int NetJoinDomain(String lpServer,
                             String lpDomain,
                             String lpAccountOU,
                             String lpAccount,
                             String lpPassword,
                             int fJoinOptions);

    /*
     * Net unjoin domain
     * */
    public int NetUnjoinDomain(
            String lpServer,
            String lpAccount,
            String lpPassword,
            int fUnjoinOptions
    );
};



