package com.xq.gymnasium.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 字符串操作函数
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class StringTools {
	private static StringTools tools = null;

	/**
	 * 屏蔽关键词
	 */
	private static String[] MaskWord = { "法论功", "李宏志", "大纪元", "真善忍", "新唐人", "肉棍",
			"淫靡", "淫水", "六四事件", "迷药", "迷昏药", "窃听器", "六合彩", "买卖枪支", "退党", "三唑仑",
			"麻醉药", "麻醉乙醚", "短信群发器", "帝国之梦", "毛一鲜", "黎阳平", "色情服务", "对日强硬",
			"出售枪支", "迷药", "摇头丸", "西藏天葬", "鬼村", "军长发威", "PK黑社会", "恶搞晚会", "枪决女犯",
			"投毒杀人", "强硬发言", "出售假币", "监听王", "昏药", "侦探设备", "麻醉钢枪", "反华", "官商勾结",
			"升达毕业证", "手机复制", "戴海静", "自杀指南", "自杀手册", "张小平", "佳境安定片", "蒙汗药",
			"古方迷香", "强效失忆药", "迷奸药", "透视眼镜", "远程偷拍", "自制手枪", "子女任职名单", "激情小电影",
			"黄色小电影", "色情小电影", "天鹅之旅", "盘古乐队", "高校暴乱", "催情药", "拍肩神药", "春药",
			"窃听器材", "身份证生成器", "枪决现场", "出售手枪", "麻醉枪", "办理证件", "办理文凭", "藏独",
			"疆独", "高干子弟", "高干子女", "枪支弹药", "血腥图片", "反政府", "禁书", "无界浏览器", "特码",
			"反共", "成人片", "成人电影", "换妻", "三级片", "代开发票", "北京办证", "办证", "文凭入网",
			"小姐", "SM", "法轮功", "淫荡", "卖淫" };

	
	/**
	 * 不允许注册的用户名
	 */
	private String[] NoName = { "匿名", "留学专搜", "吧主", "副吧主", "管理员", "网站管理员",
			"贴吧吧主", "诚信章", "诚信章公司" };

	/**
	 * 过滤URL时，可以存在的URL
	 */
	private String[] furls = { "zhuansoo.", "521soo.", "zhuansou.", "mailto:" };

	/**
	 * 每个 全站 页的meta标签metaDescription值
	 */
	public String metaDescription_def = "";

	/**
	 * 每个 全站 页的 meta标签 keyword 值
	 */
	public String metaKeywords_def = "留学,留学生,贴吧,微吧";

	/**
	 * 院校页面meta标签 keyword 值
	 */
	public String metaKeywords_sch = "留学,国外院校,美国大学,院校库,世界大学排名,欧洲留学,院校对比,院校筛选";
	
	/**
	 * 院校页面meta标签 metaDescription 值
	 */
	public String metaDescription_sch = "院校汇集了世界各地的大学、学院、中学、预科的院校信息。通过院校筛选，方便快捷的找到目标院校，查看国外院校，也可以加入院校对比查看。";
	
	
	/**
	 * 每个bar页的 meta标签 keyword 值
	 */
	public String metaKeywords_bar = "留学微吧,贴吧,微吧,留学生,托福,GRE,雅思,小语种,资料下载";
	/**
	 * 每个 bar 页的meta标签metaDescription值
	 */
	public String metaDescription_bar = "留学专搜微吧是最具影响力的留学生社区，集出国、留学、移民、海外生活、及托福，GRE，雅思，小语种学习资料下载于一体的互动交流社区。";

	/**
	 * 每个 cir 页的 meta标签 keyword 值
	 */
	public String metaKeywords_cir = "留学,留学生活,留学圈,交友,大学,个人主页,大话";

	/**
	 * 每个cir页的meta标签metaDescription值
	 */
	public String metaDescription_cir = "加入留学专搜圈YOU你可以：记录你的留学生活，发表大话，考试心得、结交志同道合的留学朋友，了解他们的大学生活的最新动态，分享你生活中发生的点点滴滴。";
	
	/**
	 * 每个 解疑 页的 meta标签 keyword 值
	 */
	public String metaKeywords_doubt = "留学咨询,出国留学,签证申请,行前安排,结伴同行,留学生活,留学签证,留学中介,院校申请,移民";
	
	/**
	 * 每个 解疑 页的meta标签metaDescription值
	 */
	public String metaDescription_doubt = "留学专搜解疑是由国内最专业的留学咨询、解疑平台。提供留学咨询，出国留 学，签证申请, 行前安排,结伴同行,留学生活,留学签证,留学中介，院校申请,移民等问题的解答。";
	
	/**
	 * 每个 诚信章 页的 meta标签 keyword 值
	 */
	public String metaKeywords_member = "留学,留学中介,出国中介,出国留学机构，培训机构，院校库";
	
	/**
	 * 每个 诚信章 页的meta标签metaDescription值
	 */
	public String metaDescription_member = "诚信章是留学专搜全面推出的网络诚信会员服务,以'功自诚心,利从信来'为理念,全力打造国际性互联网留学信用体系.为学生提供院校库，找诚信留学中介，培训机构，出国中介,出国留学机构,培训学校到留学专搜诚信章";

	/**
	 * 收藏模块:微题和解疑 content>%s依次为:模块名称-微题链接-标题-作者个人中心链接-作者-时间-内容
	 */
	public String vtAndDoubtModel = "<div style='float:left;width:700px;'><div class='sc_center2_a'>" +
			"<span class='sc_span6'>%s:</span></div>" +
			"<div class='sc_center2_b'><ul>" +
			"<li><a href='%s' target='_top'>%s</a><span class='sc_span5'><a href='%s' target='_top'>%s</a></span>" +
			"<span class='sc_span5'>%s</span></li>" +
			"<li>%s</li></ul></div></div>";
	/**
	 * 收藏模块:大话 content>%s依次为:模块名称-大话内容(包含话题)-时间
	 */
	public String boastModel = "<span style='float:left;'><span class='sc_span6'>%s:</span>%s</span><span class='sc_span5'>%s</span>";
	/**
	 * 收藏模块：微吧content>%s依次为:模块名称-微吧链接-微吧名称-吧链接-吧头像-微题数量-吧主-成员数量-建立时间-微吧支持度
	 */
	/*public String vbarModel = "<div class='sc_center2_e'><span class='sc_span6'>%s：</span><a href='%s'>%s</a></div><div class='sc_center2_f'><ul><li class='hg'><a href='%s'><img src='%s' border='0'/></a></li><li>微题:%s</li><li>吧主:%s</li><li>成员:%s</li></ul><ul style='width:138px;'><li>建立日期：%s</li><li>微吧支持度：%s</li></ul><p class='sc_p2'>&nbsp;</p></div>";*/
	
	/**
	 * 操作限制后的提示信息
	 */
	public String operationMess = "<script>alert('您的操作太频繁，请休息一会再试');</script>";
	
	/**
	 * 回复可见
	 */
	public String repleView = "<div class='vt_d2_b'>本内容回复可见</div>";
	
	/**
	 * 购买可见
	 */
	public String buyView = "<div class='vt_d2_b'><span class='z_sw'>本内容购买可见，售价%n金币（购买后永久有效）</span><input type='button' id='buyThemeButt' value='购买' class='an' /></div>";
	
	/**
	 * 登录后可见
	 */
	public String loginView = "<div class='vt_d2_b'>本内容登录后可见</div>";
	
	private StringTools() {
		init();
	}

	/**
	 * 该类的初始化方法
	 */
	public void init() {

	}

	/**
	 * 重置该类
	 */
	public void reset() {
		init();
	}

	/**
	 * 工厂方法，返回
	 * 
	 * @return
	 */
	public static StringTools getFactory() {
		if (tools == null) {
			create();
		}
		return tools;
	}

	/**
	 * 线程安全的创建方法，防止并发多次创建
	 */
	private static synchronized void create() {
		if (tools == null) {
			tools = new StringTools();
		}
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 */
	public String encodeBase64Encoder(String str) {
		BASE64Encoder be = new BASE64Encoder();
		str = be.encode(str.getBytes());
		str = be.encode(str.getBytes());
		return str;
	}

	/**
	 * 解密
	 * 
	 * @param str
	 * @return
	 */
	public String decodeBase64Encoder(String str) {
		BASE64Decoder bd = new BASE64Decoder();
		try {
			str = new String(bd.decodeBuffer(str));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("64位解码①失败!!");
			return "";
		}
		try {
			str = new String(bd.decodeBuffer(str));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("64位解码②失败!!");
			return "";
		}
		return str;
	}

	/**
	 * 分割 省市
	 * 
	 * @param s
	 * @return
	 */
	public String[] splitProvinceCity(String s) {
		if (isNullOrEmpty(s)) {
			return null;
		}
		String ss[] = s.split("\\-");
		return isNullOrEmpty(ss) ? null : ss;
	}

	/**
	 * 判断一组字符串是否为空
	 * 
	 * @param str
	 * @return true空 false不为空
	 */
	public boolean isNullOrEmpty(String str, String... strs) {
		for (String s : strs) {
			if (!isNullOrEmpty(s)) {
				return false;
			}
		}
		return isNullOrEmpty(str);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return true空 false不为空
	 */
	public boolean isNullOrEmpty(String str) {
		return str == null || str.trim().equals("");
	}

	/**
	 * 判断字符串数组是否含有空元素
	 * 
	 * @param str
	 *            字符串数组
	 * @return true数组中含有空元素 false数组中无空元素
	 */
	public boolean isNullOrEmpty(String[] str) {
		if (str == null)
			return true;
		for (String s : str) {
			if (isNullOrEmpty(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据提交类型，将字符串编码转换成UTF-8
	 * 
	 * @param s
	 *            字符串
	 * @param methodType
	 *            提交类型
	 * @return
	 */
	public String getUTF8String(String s, String methodType) {
		if (!isNullOrEmpty(methodType)
				&& methodType.toLowerCase().equals("post")) {
			return s;
		}
		return getUTF8String(s);
	}

	/**
	 * 将字符串编码转换成UTF-8
	 * 
	 * @param s
	 * @return
	 */
	public String getUTF8String(String s) {
		if (isNullOrEmpty(s)) {
			return "";
		}
		try {
			return new String(s.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}
	
	/**
	 * 自动判断编码格式，判断是否需要转码
	 * @param s
	 * @return
	 */
	public String getUTF8StringAuto(String s){
		if (isNullOrEmpty(s)) {
			return "";
		}
		try {
			int count=0;
			for(int i=0;i<s.length();i++){
				if(s.charAt(i)>=127 && s.charAt(i)<=255){
					count++;
					if(count>=3){
						break;
					}
				}
			}
			if(count>=3){
				return new String(s.getBytes("iso-8859-1"), "utf-8");
			}
			return s;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}

	/**
	 * 将字符串编码转换成GBK
	 * @param s
	 * @return
	 */
	public String getGBKString(String s) {
		if(isNullOrEmpty(s)){
			return "";
		}
		try {
			return new String(s.getBytes("iso-8859-1"), "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}
	
	/**
	 * 将文字 转成 iso-8859-1
	 * @param s
	 * @return
	 */
	public String getIsoString(String s){
		if(isNullOrEmpty(s)){
			return "";
		}
		try {
			return new String(s.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}
	
	/**
	 * 字符串转URL编码
	 * 
	 * @param s
	 * @return
	 */
	public String urlEncoder(String s) {
		if (isNullOrEmpty(s)) {
			return "";
		}
		try {
			return URLEncoder.encode(s, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取屏蔽词
	 * 
	 * @return
	 */
	public String[] getMaskWord() {
		return MaskWord;
	}

	/**
	 * 获取不允许注册的用户名
	 * 
	 * @return
	 */
	public String[] getNoName() {
		return NoName;
	}

	/**
	 * 判断该用户名是否允许注册
	 * 
	 * @param userName
	 * @return true不允许注册 false允许注册
	 */
	public boolean isNoName(String userName) {
		for (String v : NoName) {
			if (v.equals(userName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串转数组
	 * 
	 * @param arg
	 *            字符串数组
	 * @return 转换后的字符串，不可能为null 最少""
	 */
	public String arraysToString(String[] arg) {
		return arraysToString(arg, null);
	}

	/**
	 * 字符串转数组
	 * 
	 * @param arg
	 *            字符串数组
	 * @param split
	 *            自定义定界符
	 * @return 转换后的字符串，不可能为null 最少""
	 */
	public String arraysToString(Object[] arg, String split) {
		if (arg == null)
			return "";
		String s = Arrays.toString(arg);
		s = s.substring(1, s.length() - 1);
		return split == null ? s : s.replace(",", split);
	}

	/**
	 * 字符串去前后空格
	 * 
	 * @param v
	 *            字符串
	 * @return v==null 返回 ""
	 */
	public String trims(String v) {
		return v == null ? "" : v.trim();
	}

	/**
	 * 转换html特殊符号
	 * 
	 * @param html
	 * @return
	 */
	public String htmlToString(String html) {
		if (html == null)
			return "";
		return html.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;").replace("\"", "&quot;")
				.replace("\'", "&#39;").replace("\n", "<br>");
	}

	/**
	 * 将文本域中的值 进行特殊符号转义
	 * 
	 * @param value
	 * @return
	 */
	public String filterInputValue(String value) {
		if (value == null)
			return "";
		return value.replace("<", "&lt;")
				.replace(">", "&gt;").replace("\"", "&quot;")
				.replace("\'", "&#39;");
	}
	
	/**
	 * 将带特殊字符的字符串转成原始字符串
	 * @param fvalue
	 * @return
	 */
	public String unFilterInputValue(String fvalue){
		if(fvalue==null)
			return "";
		return fvalue.replace("&lt;","<").replace("&gt;", ">").replace("&quot;", "\"").replace("&#39;", "\'");
	}
	
	/**
	 * 将js脚本参数的值 进行特殊符号转义
	 * 
	 * @param para
	 * @return
	 */
	public String filterJSPara(String para) {
		if (para == null)
			return "";
		return para.replaceAll("([&<>\"\'])", "\\\\$1");
	}

	/**
	 * 过滤字符串中的其他标签
	 * 
	 * @param html
	 * @return
	 */
	public String filterOtherHTML(String html) {
		if (html == null)
			return "";
		return html
				.replaceAll("[\r\n]", "")
				.replaceAll("(?i)<script.*?</script.*?>", "")
				.replaceAll("(?i)<style.*?</style.*?>", "")
				.replaceAll("<\\!\\-\\-.*?\\-\\->", "")
				.replaceAll(
						"(?i)<(?!img|embed|br|p|\\/p|u|\\/u|strong|\\/strong|em|\\/em|span|\\/span|a|\\/a|blockquote|\\/blockquote|div|\\/div|b|\\/b).*?>",
						"")
				.replaceAll(
						"(?i)on(load|unload|change|submit|reset|select|blur|focus|keydown|keypress|keyup|click|dblclick|mousedown|mousemove|mouseout|mouseover|mouseup)",
						"a").replace("javascript", "b");
	}

	/**
	 * 过滤字符串中的全部标签
	 * 
	 * @param html
	 * @return
	 */
	public String filterAllHTML(String html) {
		if (html == null)
			return "";
		return html.replaceAll("[\r\n]", "").replaceAll("(?i)<.*?>", "").replaceAll("&.{2,7};","").replace("\"", "&quot;");
	}

	/**
	 * 过滤字符串中 所有 非合法字符 只保留 中文英文数字
	 * 
	 * @param s
	 * @return
	 */
	public String filterString(String s) {
		return trims(s).replaceAll("[^\\d\\w\u4e00-\u9fa5]+", "");
	}

	/**
	 * 过滤字符串中不是指定域名中的超链接
	 * 
	 * @param html
	 *            网页内容
	 * @param urls
	 *            [] 指定的可以包含的域名'null'表示什么也不过滤 '.'表示全过滤
	 * @return 过滤后的字符串
	 */
	public String filterLink(String html, String urls[]) {
		if (urls == null)
			return html;
		String filter = "<a[^>]href\\s?=(.*?)[\\s/>].*?</\\s?a>";
		Pattern patt = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
		Matcher matc = patt.matcher(html);
		while (matc.find()) {
			String str = matc.group();
			String st = matc.group(1);
			int i = 0;
			for (; i < urls.length; i++) {
				if (st.indexOf(urls[i]) > -1) {
					break;
				}
			}
			if (i >= urls.length) {// 正常结束循环，该网址不包含在不过率列表里，所以替换
				html = html.replace(str, "");
			}
		}
		return html;
	}

	public String filterLink(String html) {
		return filterLink(html, furls);
	}

	/**
	 * 字符串截取
	 * 
	 * @param str
	 *            要截取的字符串
	 * @param end
	 *            字符串长度
	 * @return
	 */
	public String subString(String str, int end) {
		if (str == null) {
			return "";
		}
		if (str.length() < end) {
			return str;
		}
		return str.substring(0, end);
	}

	/**
	 * 截取中文字符串，中文算2个字符
	 * 
	 * @param s
	 * @param l
	 * @return
	 */
	public String subStringCN(String s, int l) {
		/**
		if (s == null)
			return "";
		try {
			byte[] bs = s.getBytes("gbk");
			if (bs.length <= l) {
				return s;
			}
			String temp = new String(bs, 0, l, "gbk");
			if ((int) temp.charAt(temp.length() - 1) > 65500) {
				if (temp.length() > 1)
					return temp.substring(0, temp.length() - 1);
				else
					return "";
			}
			return temp;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;*/
		if (s == null)
			return "";
		if(s.length()<l/2)
			return s;
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > 8192) {
				count += 2;
			} else {
				count++;
			}
			if (count > l) {
				return s.substring(0, i);
			}
		}
		return s;
	}
	
	/**
	 * 截取中文字符串，英文算0.5个，非英文算1个
	 * 
	 * @param s
	 * @param l
	 * @return
	 */
	public String subStringCN_UTF8(String s, int l) {
		return subStringCN(s,l*2);
	}
	
	/**
	 * 字符串截取带有省略号
	 * 
	 * @param str
	 *            要截取的字符串
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public String subStringCN_UTF8Ellipsis(String str, int length) {
		if(getCNLength(str)<=length*2){
			return str;
		}
		return subStringCN_UTF8(str,length-1)+"…";
	}
	/**
	 * 获取字符串字符长度
	 * 
	 * @param s
	 * @return
	 */
	public int getCNLength(String s) {
		if (s == null)
			return 0;
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > 8192) {
				count += 2;
			} else {
				count++;
			}
		}
		return count;
	}

	/**
	 * 浮点型保留2位字符，四舍五入
	 * 
	 * @param d
	 * @return #0.00
	 */
	public String formatDouble(double d) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(d);
	}

	/**
	 * 字符串转整形
	 * 
	 * @param v
	 * @return
	 */
	public int getInteger(String v) {
		return getInteger(v, 0);
	}
	/**
	 * 字符串数组转整形数组
	 * @param vs
	 * @return
	 */
	public Set<Integer> getIntegers(String vs[]) {
		Set<Integer> set = new HashSet<Integer>();
		if(vs==null){
			vs = new String[0];
		}
		for(String v:vs){
			set.add(getInteger(v));
		}
		if(set.size()>1){
			set.remove(0);
		}else if(set.isEmpty()){
			set.add(0);
		}
		return set;
	}
	
	/**
	 * 获取float 
	 * @param v string 字符串
	 * @return
	 */
	public float getFloat(String v){
		if(this.isNullOrEmpty(v)){
			return 0f;
		}
		try {
			return Float.parseFloat(v);
		} catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	/**
	 * 字符串转整形 - 带默认值
	 * 
	 * @param v
	 *            字符串
	 * @param def
	 *            默认值
	 * @return
	 */
	public int getInteger(String v, int def) {
		if(isNullOrEmpty(v))return def;
		for(int i=0;i<v.length();i++){
			if(v.charAt(i)<48 || v.charAt(i)>57){
				return def;
			}
		}
		if(v.length()>10)return def;
		return (int)Long.parseLong(v);
//		return v != null && v.matches("\\d+") ? Integer.parseInt(v) : def;
	}

	/**
	 * 格式化显示邮箱:1234@qq.com → 1**4@qq.com
	 * 
	 * @param s
	 *            邮箱
	 * @return
	 */
	public String formatEmail(String s) {
		int t = s.lastIndexOf("@")-1;
		if(t<1){
			return s;
		}
		String num = "";
		for (int i = 0; i < t-1; i++) {
			num = num.concat("*");
		}
		return s.substring(0,1).concat(num).concat(s.substring(t));
//		return s == null ? "" : s.replaceAll("(?<!^).(?=.+@.+)", "*");
	}

	/**
	 * 将 阿拉伯 数字 转成 中文数字
	 * 
	 * @param n
	 * @return
	 */
	public String getCnNumber(int n) {
		switch (n) {
		case 0:
			return "零";
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "七";
		case 8:
			return "八";
		case 9:
			return "九";
		case 10:
			return "十";
		case 11:
			return "十一";
		case 12:
			return "十二";
		}
		return "负";
	}
	
	/**
	 * 获取阿拉伯数字的位数 对应的单位
	 * @param median
	 * @return
	 */
	private String getCnNumberMedian(int median){
		switch(median){
		case 1:return "十";
		case 2:return "百";
		case 3:return "千";
		case 4:return "万";
		case 5:return "十";
		case 6:return "百";
		case 7:return "千";
		}
		return "";
	}
	
	/**
	 * 根据数字和位数返回中文
	 * @param n 数字
	 * @param median 位数 当前第几位
	 * @return
	 */
	private String getCnNumberMore(int n,int median){
		int t = n/10;
		int last = n-t*10;
		if(t==0){
			return getCnNumber(last)+getCnNumberMedian(median);
		}
		return getCnNumberMore(t,median+1)+getCnNumber(last)+getCnNumberMedian(median);
	}
	/**
	 * 将数字转换成 中文数字
	 * @param num
	 * @return
	 */
	public String numberToCn(int num){
		String s = getCnNumberMore(num,0);
		if(s.startsWith("一十")){
			s = s.substring(1);
		}
		return s.replaceAll("零.","零").replaceAll("零{2,}","零").replaceAll("零$","");
	}
	
	/**
	 * 获取收藏大话模型
	 * @param moduleName 模块名称
	 * @param boastContent 大话内容
	 * @param pic 图片
	 * @param time 时间
	 * @return
	 */
	public String getCollectBoastModel(String moduleName,String boastContent,String time){
		return String.format(this.boastModel, moduleName,boastContent,time);
	}
	/**
	 * 收藏模块:微题和解疑 content>%s依次为:模块名称-微题链接-标题-作者个人中心链接-作者-时间-内容
	 * @param moduleName 模块名称
	 * @param linkVt 微题链接
	 * @param title 标题
	 * @param userHome 作者个人中心链接
	 * @param author 作者
	 * @param time 时间
	 * @param content 内容
	 * @return
	 */
	public String getCollectDoubtOrVtModel(String moduleName,String linkVt,String title,String userHome,String author,String time,String content){
//		System.out.println(this.vtAndDoubtModel);
		return String.format(this.vtAndDoubtModel,moduleName,linkVt,title,userHome,author,time,content);
	}
	/**
	 * 在控制台输出信息
	 */
	public void print(Object... v) {
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		StringBuilder sb = new StringBuilder("日志");
		sb.append(" : ").append(items[2].toString());
		for (int i = 0; i < v.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			if(v[i]==null){
				v[i]="{null}";
			}
			sb.append(v[i].toString());
		}
		System.out.println(sb.toString());
	}
	
	/**
	 * 从最后一个 s 开始截取剩余字符串（不包含s）
	 * @param str 原始字符串
	 * @param s 要寻找的字符串
	 * @return
	 */
	public String subLastIndex(String str,String s){
		if(StringTools.getFactory().isNullOrEmpty(str)) {
			return null;
		}
		if(StringTools.getFactory().isNullOrEmpty(s)){
			return str;
		}
		int t = str.lastIndexOf(s);
		return t<0?null:str.substring(t+s.length());
	}

	private Pattern atUserPatt = Pattern.compile("@([0-9a-zA-Z_\\-\\u4e00-\\u9fa5]{2,16})",Pattern.MULTILINE);
	/**
	 * 在内容中过滤出所有的 被@的人
	 * @param content 内容
	 * @return
	 */
	public List<String> filterAtUser(String content){
		List<String> ls = new ArrayList<String>();
		if(content==null)return ls;
		Matcher matc = atUserPatt.matcher(content);
		while(matc.find()){
			ls.add(matc.group(1));
		}
		return ls;
	}
	/**
	 * 随机从数组里拿出一个元素
	 * @param array
	 * @return
	 */
	public String getRandomStrInArray(String array[]){
		if(array==null || array.length == 0){
			return "";
		}
		int i = (int)(Math.random()*array.length);
		if(i>array.length || i<0){
			i = 0;
		}
		return array[i];
	}
	
	
	
	/**
	 * 输出 程序 执行栈
	 */
	public void printStackTrace(){
	/*	StackTraceElement[] ss = Thread.currentThread().getStackTrace();
		for(StackTraceElement s:ss){
			System.out.println(s);
		}*/
	}
	
	
	
	
	
	
	private String baseRepWord = "点击下载";
	private String baseRege = "(?s)<a.*?"+baseRepWord+".*?</a>";
	/**
	 * 过滤登录下载区域
	 * @param html
	 * @return
	 */
	public String filterLoginHidden(String html){
		if(html.indexOf(baseRepWord)>-1){//存在 需要替换的字符串
			html = html.replaceAll(baseRege,this.loginView);
	  	}
		return html;
	}
	
	
	
	
	
	/**
	 * 过滤IMG 的正则
	 * 
	 */
	private Pattern patt = Pattern.compile("<img[^>]+src\\s?=[\\s'\"]?([^ \'\"]+)[\\s'\"]?.*?>");
	/**
	 * 过滤html中的img标签
	 * @param html
	 * @return
	 */
	public List<String> filterImgsRege(String html){
		List<String> ls = new ArrayList<String>();
		Matcher matc = patt.matcher(html);
		while(matc.find()){
			String img = matc.group(1);
			if(img.indexOf("/kedit/plugins/emoticons/")>-1){
				continue;
			}
			ls.add(img);
		}
		return ls;
	}
	/**
	 * 搜索过滤分词
	 * @param content 准备过滤的内容
	 * @param word 需要过滤的单词
	 * @param type null 为标题 否则为内容
	 * @return
	 */
	public String filterSearchContent(String content,String word,String type){
		String nw2[] = word.replace(" and "," ").split(" ");
		String ma = type == null ? "ma" : "ma1";
		for(String s : nw2){
			content = content.replaceAll("(?i)"+s+"", "<span class=\""+ma+"\">"+s+"</span>");
		}
		return content;
	}
	/**
	 * 搜索过滤分词
	 * @param content 准备过滤的内容
	 * @param word 需要过滤的单词
	 * @param type null 为标题 否则为内容
	 * @return
	 */
	public String filterSearchContentNormal(String content,String word,String type){
		String ma = type == null ? "ma" : "ma1";
		return content = content.replaceAll("(?i)"+word+"", "<span class=\""+ma+"\" style='color:#fe6500;'>"+word+"</span>"); 
	}

	/**
	 * 用特定符号将集合关联成一个字符串
	 * @param symbol
	 * @return
	 */
	public String getJoin(String symbol,Collection coll){
		if(coll==null)return "";
		
		StringBuilder sb = new StringBuilder("");
		List ls = new ArrayList(coll);
		for(int i=0;i<ls.size();i++){
			sb.append(symbol).append(ls.get(i).toString());
		}
		return sb.toString();
	}
	
	/**
	 * 将 map 中的对象 转成 int
	 * 
	 * @author 创建人：
	 * @version 创建于：2015年7月17日 下午3:51:09
	 * @author 修改人：
	 * @version 修改于：
	 * @param obj
	 * @param def
	 * @return
	 */
	public int getIntegerForObj(Object obj , int def){
		return (int)getLongForObj(obj,def);
	}
	public int getIntegerForObj(Object obj){
		return getIntegerForObj(obj, 0);
	}
	/**
	 * 将 map 中的对象 转成 long
	 * 
	 * @author 创建人：
	 * @version 创建于：2015年7月17日 下午3:51:09
	 * @author 修改人：
	 * @version 修改于：
	 * @param obj
	 * @param def
	 * @return
	 */
	public long getLongForObj(Object obj , int def){
		if(obj instanceof Long){
			Long l = (Long)obj;
			return l==null?def:l;
		}
		if(obj instanceof Integer){
			Integer i = (Integer)obj;
			return i==null?def:i;
		}
		if(obj instanceof BigDecimal){
			BigDecimal bd = (BigDecimal)obj;
			return bd==null?def:bd.longValue();
		}
		return def;
	}
	public long getLongForObj(Object obj){
		return getLongForObj(obj, 0);
	}
	
	/**
	 * 判断对象是否为空
	 * yangweihang
	 * @Date 2018年8月25日 下午6:35:58
	 * @param str
	 * @return
	 */
	public boolean isNull(Object str) {
		return str == null;
	}
}
