package com.api.ivr.global.constants;

public class GlobalConstants {
	
	public static String SUCCESSCODE = "000000";
	public static String FAILURECODE = "111111";
	public static String SUCCESS = "SUCCESS";
	public static String FAILURE = "FAILURE";
	public static String CONTENTTYPE="contenttype";
	public static String TYPE="type";
	public static String FAILURECODE_UNKNOWN = "111111";
    public static String ERRORCODE_MOBILENUMBER_NULL="800101";
    public static String ERRORCODE_MOBILENUMBEREMPTY="800102";
	public static String ERRORCODE_INVALIDKEYEXCEPTION_700001 = "700001";
	public static String ERRORCODE_RELID_NULL="800103";
	public static String ERRORCODE_OTPBLOCKED="001368";
	public static String ERRORCODE_OTPINPUT_NULL="710000";
	public static String ERRORCODE_OTPINPUT_EMPTY="710001";
	public static String ERRORCODE_PROFILE_NOTFOUND="CUS0001";
	public static String ERRORCODE_ACCNUM_NULL="410001";
	public static String ERRORCODE_ACCNUM_EMPTY="410002";
	public static String ERRORCODE_CURRENCYCODE_NULLOREMPTY="410003";
	public static String ERRORCODE_CARDNUMBER_NOTFOUND="099990";
	public static String ERRORCODE_INVALID_DEBITCARD="000014";
	public static String ERRORCODE_CARDNUMBER_NULL="300001";
	public static String ERRORCODE_CARDNUMBER_EMPTY="300002";
	public static String ERRORCODE_RELID_EMPTY="800104";
	public static String ERRORCODE_CLIID_NULL="800201";
	public static String ERRORCODE_CLIID_EMPTY="800202";
	//public static String ERRORCODE_JSONGENERATIONEXCEPTION_700002 = "700002";
	//public static String ERRORCODE_JSONMAPPINGEXCEPTION_700003 = "700003";
	public static String ERRORCODE_IOEXCEPTION_700002 = "700002";
	//public static String ERRORCODE_JAXBEXCEPTION_700005 = "700005";
	//public static String ERRORCODE_NOSUCHALGORITHMEXCEPTION_700006 = "700006";
	//public static String ERRORCODE_NOSUCHPADDINGEXCEPTION_700007 = "700007";
	//public static String ERRORCODE_INVALIDKEYSPECEXCEPTION_700008 = "700008";
	public static String ERRORCODE_NAMINGEXCEPTION_700003 = "700003";
	public static String ERRORCODE_NULLPOINTEREXCEPTION_700004 = "700004";
	//public static String ERRORCODE_EXCEPTION_700012 = "700012";
	//public static String ERRORCODE_INVALID_INPUTS_FROM_IVR_700013 = "700013";
	//public static String ERRORCODE_UNKNOWNHOSTEXCEPTION_700014 = "700014";
	public static String ERRORCODE_PARSEEXCEPTION_700005 = "700005";
	public static String ERRORCODE_URISYNTAXEXCEPTION_700006 = "700006";
	//public static String ERRORCODE_KEYMANAGEMENTEXCEPTION_700017 = "700017";
	//public static String ERRORCODE_HOSTEXCEPTION_700020 = "700020";
	//public static String ERRORCODE_TOKENISNULL_700021 = "700021";
	//public static String ERRORCODE_EXCEPTIONINTOKEN_700022 = "700022";
	//public static String ERRORCODE_HOSTRESPONSEISNULL_700023 = "700023";
	public static String ERRORCODE_SOCKETEXCEPTION_700007 = "700007";
	public static String ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008 = "700008";
	public static String ERRORCODE_JSONPARSEEXCEPTION_700009 = "700009";
	public static String ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010 = "700010";
	

	public static String ERRORCODE_INVALID_INPUTS_FROM_IVR_700011 = "700011";
	public static String ERRORCODE_SERVICE_PROP_NOT_FOUND_700012 = "700012";
	public static String ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013 = "700013";
	public static String ERRORCODE_TOKEN_GEN_700014 = "700014";
	public static String ERRORCODE_RESPONSE_IS_EMPTY_700015= "700015";
	
	public static String ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031= "700031";
	public static String ERRORCODE_DB_EXCEPTION_700032= "700032";
	
	public static String ERRORCODE_HOST_TPIN_BLOCKED_700041= "700041";
	
	public static String ERRORCODE_HOST_SMS_DEL_FAILED_700051= "700051";
	
	public static String ERRORCODE_REQLOANNUMBER_EMPTY="400101";
	public static String ERRORCODE_LOANNUMBERLENGETH="400201";
	public static String ERRORCODE_LOANNUMBER_NULL="400002";
	public static String ERRORCODE_PROFILEID_MISMATCH="500101";
	public static String ERRORCODE_RESLOANNUMBER_EMPTY="500201";
	public static String ERRORCODE_LOANISSUEDATE="500301";
	public static String ERRORCODE_SESSIONID_NULL="400003";
    public static String ERRORCODE_FILE_NOTFOUND="600101";
    
	public static String Dummy_Flag_Y = "Y";
	public static String Dummy_Flag_N = "N";
	public static String Config = "Config.properties";
	public static String Operations = "Operations.properties";
	public static String Parametric = "Parametric.properties";
	
	/// C400 Service
		public static String HostLog_C400 = "HostC400";
		public static String TPSystem_C400 = "C400";
		public static String HostLog = "HostLog";

		/// Ebbs Service
		public static String HostLog_Ebbs = "HostEbbs";
		public static String TPSystem_Ebbs = "EBBS";

		/// Ebbs Service
		public static String HostLog_Euronet = "HostEuronet";
		public static String TPSystem_Euronet = "Euronet";

		/// UAAS Service
		public static String HostLog_UAAS = "HostUAAS";
		public static String TPSystem_UAAS = "UAAS";

		/// UAAS Service
		public static String HostLog_CASAS = "HostCASAS";
		public static String TPSystem_CASAS = "CASAS";

		public static String HostLog_DB = "HostDB";
		public static String HostLog_JUNIT = "HostJunit";
		public static String HostLog_ARUN="HostARUN";
		public static String TPSystem_ARUN="ARUN";

		public static String HostSessionLog = "HostSessionLog";
		
		/// UAAS Service
		public static String HostLog_MDIS = "HostMDIS";
		public static String TPSystem_MDIS = "MDIS";
		
		/// Date Format
		public static String DateTimeFormat = "yyyy-MM-dd HH:mm:ss";
		public static String dateFormat = "yyyy-MM-dd";
		public static String timeFormat = "HH:mm:ss";

}
