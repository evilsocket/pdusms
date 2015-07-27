package com.evilsocket.pdusms;

import java.util.HashMap;

/**
 * Created by evilsocket on 11/05/15.
 */

// !!! http://mobiletidings.com/2009/02/20/wap-push-over-sms/ !!!
// !!! http://mobiletidings.com/2009/02/21/wap-push-over-sms-encodings/ !!!

// http://osxr.org/android/source/frameworks/base/telephony/java/com/android/internal/telephony/SmsHeader.java#0072
// https://android.googlesource.com/platform/frameworks/opt/telephony/+/3f4415e31c8c407e0397511fdc2d5906361295fa/src/java/com/android/internal/telephony/WapPushOverSms.java
public class WspPDU
{
    public static final short WAP_PUSH_PORT = 2948;

    private static final int PAYLOAD_HEADER_SIZE = 16;
    private static final int PAYLOAD_CONST_SIZE = PAYLOAD_HEADER_SIZE + 2;

    public static final byte PDU_TYPE_PUSH = 0x06;
    public static final byte PDU_TYPE_CONFIRMED_PUSH = 0x07;

    // The WSP transaction ID
    public byte transactionId = 0x0A;

    // PUSH pdu type
    private byte pduType = PDU_TYPE_PUSH;

    // Headers consist of 4 octets
    private byte headerSize = 0x04;

    // Content type consists of 3 octets
    private byte contentTypeSize = 0x03;
    // Encoded content type 0x30: This stands for  application/vnd.wap.slc (a WBXML encoded SL document)
    private byte contentType = (byte)( 0x30 | 0x80 );

    // Parameter name: 0x01 means 'Charset'
    private byte charsetParamName = (byte)( 0x80 | 0x01 );
    // Parameter value: 0x6A means 'UTF-8'
    private byte charsetParamValue = (byte)( 0x6A | 0x80 );

    // Encoding UTF-8
    private byte encoding = (byte)0x6A;

    // WBXML Version 1.2
    private byte wbxmlVersion = 0x02;
    // Document type -//WAPFORUM//DTD SL 1.0//EN
    private byte documentType = 0x06;

    // String table length
    private byte stringTableLength = 0x00;
    // <sl> element, with attributes
    private byte slElementStart = (byte)0x85;
    // token for 'href=http://'
    private byte httpToken = 0x09;
    // Inline string follows
    private byte inlineStringCmd = 0x03;
    // Inline string termination
    private byte stringTermination = (byte)0x00;
    // end of <sl> attributes
    private byte slElementEnd = 0x01;

    private String url = "www.google.com";

    private final static HashMap<String, Integer> CONTENT_TYPES = new HashMap<String, Integer>();
    private final static HashMap<String, Integer> CHARSETS = new HashMap<String, Integer>();

    static {
        CONTENT_TYPES.put("*/*", 0x00);
        CONTENT_TYPES.put("text/*", 0x01);
        CONTENT_TYPES.put("text/html", 0x02);
        CONTENT_TYPES.put("text/plain", 0x03);
        CONTENT_TYPES.put("text/x-hdml", 0x04);
        CONTENT_TYPES.put("text/x-ttml", 0x05);
        CONTENT_TYPES.put("text/x-vCalendar", 0x06);
        CONTENT_TYPES.put("text/x-vCard", 0x07);
        CONTENT_TYPES.put("text/vnd.wap.wml", 0x08);
        CONTENT_TYPES.put("text/vnd.wap.wmlscript", 0x09);
        CONTENT_TYPES.put("text/vnd.wap.wta-event", 0x0A);
        CONTENT_TYPES.put("multipart/*", 0x0B);
        CONTENT_TYPES.put("multipart/mixed", 0x0C);
        CONTENT_TYPES.put("multipart/form-data", 0x0D);
        CONTENT_TYPES.put("multipart/byterantes", 0x0E);
        CONTENT_TYPES.put("multipart/alternative", 0x0F);
        CONTENT_TYPES.put("application/*", 0x10);
        CONTENT_TYPES.put("application/java-vm", 0x11);
        CONTENT_TYPES.put("application/x-www-form-urlencoded", 0x12);
        CONTENT_TYPES.put("application/x-hdmlc", 0x13);
        CONTENT_TYPES.put("application/vnd.wap.wmlc", 0x14);
        CONTENT_TYPES.put("application/vnd.wap.wmlscriptc", 0x15);
        CONTENT_TYPES.put("application/vnd.wap.wta-eventc", 0x16);
        CONTENT_TYPES.put("application/vnd.wap.uaprof", 0x17);
        CONTENT_TYPES.put("application/vnd.wap.wtls-ca-certificate", 0x18);
        CONTENT_TYPES.put("application/vnd.wap.wtls-user-certificate", 0x19);
        CONTENT_TYPES.put("application/x-x509-ca-cert", 0x1A);
        CONTENT_TYPES.put("application/x-x509-user-cert", 0x1B);
        CONTENT_TYPES.put("image/*", 0x1C);
        CONTENT_TYPES.put("image/gif", 0x1D);
        CONTENT_TYPES.put("image/jpeg", 0x1E);
        CONTENT_TYPES.put("image/tiff", 0x1F);
        CONTENT_TYPES.put("image/png", 0x20);
        CONTENT_TYPES.put("image/vnd.wap.wbmp", 0x21);
        CONTENT_TYPES.put("application/vnd.wap.multipart.*", 0x22);
        CONTENT_TYPES.put("application/vnd.wap.multipart.mixed", 0x23);
        CONTENT_TYPES.put("application/vnd.wap.multipart.form-data", 0x24);
        CONTENT_TYPES.put("application/vnd.wap.multipart.byteranges", 0x25);
        CONTENT_TYPES.put("application/vnd.wap.multipart.alternative", 0x26);
        CONTENT_TYPES.put("application/xml", 0x27);
        CONTENT_TYPES.put("text/xml", 0x28);
        CONTENT_TYPES.put("application/vnd.wap.wbxml", 0x29);
        CONTENT_TYPES.put("application/x-x968-cross-cert", 0x2A);
        CONTENT_TYPES.put("application/x-x968-ca-cert", 0x2B);
        CONTENT_TYPES.put("application/x-x968-user-cert", 0x2C);
        CONTENT_TYPES.put("text/vnd.wap.si", 0x2D);
        CONTENT_TYPES.put("application/vnd.wap.sic", 0x2E);
        CONTENT_TYPES.put("text/vnd.wap.sl", 0x2F);
        CONTENT_TYPES.put("application/vnd.wap.slc", 0x30);
        CONTENT_TYPES.put("text/vnd.wap.co", 0x31);
        CONTENT_TYPES.put("application/vnd.wap.coc", 0x32);
        CONTENT_TYPES.put("application/vnd.wap.multipart.related", 0x33);
        CONTENT_TYPES.put("application/vnd.wap.sia", 0x34);
        CONTENT_TYPES.put("text/vnd.wap.connectivity-xml", 0x35);
        CONTENT_TYPES.put("application/vnd.wap.connectivity-wbxml", 0x36);
        CONTENT_TYPES.put("application/pkcs7-mime", 0x37);
        CONTENT_TYPES.put("application/vnd.wap.hashed-certificate", 0x38);
        CONTENT_TYPES.put("application/vnd.wap.signed-certificate", 0x39);
        CONTENT_TYPES.put("application/vnd.wap.cert-response", 0x3A);
        CONTENT_TYPES.put("application/xhtml+xml", 0x3B);
        CONTENT_TYPES.put("application/wml+xml", 0x3C);
        CONTENT_TYPES.put("text/css", 0x3D);
        CONTENT_TYPES.put("application/vnd.wap.mms-message", 0x3E);
        CONTENT_TYPES.put("application/vnd.wap.rollover-certificate", 0x3F);
        CONTENT_TYPES.put("application/vnd.wap.locc+wbxml", 0x40);
        CONTENT_TYPES.put("application/vnd.wap.loc+xml", 0x41);
        CONTENT_TYPES.put("application/vnd.syncml.dm+wbxml", 0x42);
        CONTENT_TYPES.put("application/vnd.syncml.dm+xml", 0x43);
        CONTENT_TYPES.put("application/vnd.syncml.notification", 0x44);
        CONTENT_TYPES.put("application/vnd.wap.xhtml+xml", 0x45);
        CONTENT_TYPES.put("application/vnd.wv.csp.cir", 0x46);
        CONTENT_TYPES.put("application/vnd.oma.dd+xml", 0x47);
        CONTENT_TYPES.put("application/vnd.oma.drm.message", 0x48);
        CONTENT_TYPES.put("application/vnd.oma.drm.content", 0x49);
        CONTENT_TYPES.put("application/vnd.oma.drm.rights+xml", 0x4A);
        CONTENT_TYPES.put("application/vnd.oma.drm.rights+wbxml", 0x4B);
        CONTENT_TYPES.put("application/vnd.wv.csp+xml", 0x4C);
        CONTENT_TYPES.put("application/vnd.wv.csp+wbxml", 0x4D);
        CONTENT_TYPES.put("application/vnd.syncml.ds.notification", 0x4E);
        CONTENT_TYPES.put("audio/*", 0x4F);
        CONTENT_TYPES.put("video/*", 0x50);
        CONTENT_TYPES.put("application/vnd.oma.dd2+xml", 0x51);
        CONTENT_TYPES.put("application/mikey", 0x52);
        CONTENT_TYPES.put("application/vnd.oma.dcd", 0x53);
        CONTENT_TYPES.put("application/vnd.oma.dcdc", 0x54);
        CONTENT_TYPES.put("application/vnd.uplanet.cacheop-wbxml", 0x0201);
        CONTENT_TYPES.put("application/vnd.uplanet.signal", 0x0202);
        CONTENT_TYPES.put("application/vnd.uplanet.alert-wbxml", 0x0203);
        CONTENT_TYPES.put("application/vnd.uplanet.list-wbxml", 0x0204);
        CONTENT_TYPES.put("application/vnd.uplanet.listcmd-wbxml", 0x0205);
        CONTENT_TYPES.put("application/vnd.uplanet.channel-wbxml", 0x0206);
        CONTENT_TYPES.put("application/vnd.uplanet.provisioning-status-uri", 0x0207);
        CONTENT_TYPES.put("x-wap.multipart/vnd.uplanet.header-set", 0x0208);
        CONTENT_TYPES.put("application/vnd.uplanet.bearer-choice-wbxml", 0x0209);
        CONTENT_TYPES.put("application/vnd.phonecom.mmc-wbxml", 0x020A);
        CONTENT_TYPES.put("application/vnd.nokia.syncset+wbxml", 0x020B);
        CONTENT_TYPES.put("image/x-up-wpng", 0x020C);
        CONTENT_TYPES.put("application/iota.mmc-wbxml", 0x0300);
        CONTENT_TYPES.put("application/iota.mmc-xml", 0x0301);
        CONTENT_TYPES.put("application/vnd.syncml+xml", 0x0302);
        CONTENT_TYPES.put("application/vnd.syncml+wbxml", 0x0303);
        CONTENT_TYPES.put("text/vnd.wap.emn+xml", 0x0304);
        CONTENT_TYPES.put("text/calendar", 0x0305);
        CONTENT_TYPES.put("application/vnd.omads-email+xml", 0x0306);
        CONTENT_TYPES.put("application/vnd.omads-file+xml", 0x0307);
        CONTENT_TYPES.put("application/vnd.omads-folder+xml", 0x0308);
        CONTENT_TYPES.put("text/directory;profile=vCard", 0x0309);
        CONTENT_TYPES.put("application/vnd.wap.emn+wbxml", 0x030A);
        CONTENT_TYPES.put("application/vnd.nokia.ipdc-purchase-response", 0x030B);
        CONTENT_TYPES.put("application/vnd.motorola.screen3+xml", 0x030C);
        CONTENT_TYPES.put("application/vnd.motorola.screen3+gzip", 0x030D);
        CONTENT_TYPES.put("application/vnd.cmcc.setting+wbxml", 0x030E);
        CONTENT_TYPES.put("application/vnd.cmcc.bombing+wbxml", 0x030F);
        CONTENT_TYPES.put("application/vnd.docomo.pf", 0x0310);
        CONTENT_TYPES.put("application/vnd.docomo.ub", 0x0311);
        CONTENT_TYPES.put("application/vnd.omaloc-supl-init", 0x0312);
        CONTENT_TYPES.put("application/vnd.oma.group-usage-list+xml", 0x0313);
        CONTENT_TYPES.put("application/oma-directory+xml", 0x0314);
        CONTENT_TYPES.put("application/vnd.docomo.pf2", 0x0315);
        CONTENT_TYPES.put("application/vnd.oma.drm.roap-trigger+wbxml", 0x0316);
        CONTENT_TYPES.put("application/vnd.sbm.mid2", 0x0317);
        CONTENT_TYPES.put("application/vnd.wmf.bootstrap", 0x0318);
        CONTENT_TYPES.put("application/vnc.cmcc.dcd+xml", 0x0319);
        CONTENT_TYPES.put("application/vnd.sbm.cid", 0x031A);
        CONTENT_TYPES.put("application/vnd.oma.bcast.provisioningtrigger", 0x031B);

        CHARSETS.put( "big5", 0x07EA );
        CHARSETS.put( "iso-10646-ucs-2", 0x03E8 );
        CHARSETS.put( "iso-8859-1", 0x04 );
        CHARSETS.put( "iso-8859-2", 0x05 );
        CHARSETS.put( "iso-8859-3", 0x06 );
        CHARSETS.put( "iso-8859-4", 0x07 );
        CHARSETS.put( "iso-8859-5", 0x08 );
        CHARSETS.put( "iso-8859-6", 0x09 );
        CHARSETS.put( "iso-8859-7", 0x0A );
        CHARSETS.put( "iso-8859-8", 0x0B );
        CHARSETS.put( "iso-8859-9", 0x0C );
        CHARSETS.put( "shift_JIS", 0x11 );
        CHARSETS.put( "us-ascii", 0x03 );
        CHARSETS.put( "utf-8", 0x6A );
    }

    public void setPduType( byte type ) {
        pduType = type;
    }

    public void setContentType( String name ) throws Exception {
        if( CONTENT_TYPES.containsKey(name) ){
            contentType = (byte)( CONTENT_TYPES.get(name) | 0x80 );
        }
        else {
            throw new Exception( "Invalid content type specified." );
        }
    }

    public void setCharset( String name ) throws Exception {
        if( CHARSETS.containsKey(name) ){
            charsetParamValue = (byte)( CHARSETS.get(name) | 0x80 );
            encoding = CHARSETS.get(name).byteValue();
        }
        else {
            throw new Exception( "Invalid charset specified." );
        }
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    public byte[] getBytes() {
        byte[] burl = url.getBytes();
        int url_size = url.length();
        byte[] req = new byte[PAYLOAD_CONST_SIZE + url_size];

        req[0]  = transactionId;
        req[1]  = pduType;
        req[2]  = headerSize;
        req[3]  = contentTypeSize;
        req[4]  = contentType;
        req[5]  = charsetParamName;
        req[6]  = charsetParamValue;
        req[7]  = wbxmlVersion;
        req[8]  = documentType;
        req[9]  = encoding;
        req[10] = stringTableLength;
        req[11] = slElementStart;
        req[12] = httpToken;
        req[13] = inlineStringCmd;

        int i, end = PAYLOAD_HEADER_SIZE + url_size;
        for( i = PAYLOAD_HEADER_SIZE; i < end; ++i ) {
            req[i] = burl[ i - PAYLOAD_HEADER_SIZE ];
        }

        req[end]     = stringTermination;
        req[end + 1] = slElementEnd;

        return req;
    }
}
