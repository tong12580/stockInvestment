package com.stock.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.stock.common.util.Encryption;
import com.stock.common.util.response.ServiceAction;


/**
 * @ClassName: SendSMSUtil
 * @Description: 发短信工具类
 * @author guosheng.zhu
 * @date 2011-12-26 下午05:21:31
 */
public class SendSMSUtil extends ServiceAction{
	private static Logger logger = Logger.getLogger(SendSMSUtil.class);
	private static String smsUC = "075522124208";
	private static String smsPWD = "Legadmi1";
	private static Integer smsPID = 1;
	private static String smsCallbackUrl = "http://127.0.0.1";
	private static String registerUrl = "http://202.105.212.146:8080/jboss-net/services/Register";
	private static String sendUrl = "http://202.105.212.146:8080/jboss-net/services/SendSMS";

	private static String[] hiddenText = new String[] {
			"法轮大法",
			"法轮功",
			"法轮",
			"法论",
			"法抡",
			"法伦",
			"发轮",
			"发轮功",
			"发论",
			"邓小平",
			"六合彩",
			"6合彩",
			"达赖喇嘛",
			"达赖",
			"6.4事件",
			"6.4天安门事件",
			"64事件",
			"64天安门",
			"六.四事件",
			"六.四天安门",
			"六四事件",
			"六四天安门",
			"6.4惨案",
			"64惨案",
			"六四惨案",
			"六.四惨案",
			"六四",
			"西藏独立",
			"新疆独立",
			"台湾独立",
			"东突",
			"江泽民",
			"邓小平",
			"朱\uFFFDg基",
			"李鹏",
			"温家宝",
			"胡锦涛",
			"黑牛豆奶",
			"做帐",
			"低扣",
			// 以下内容增加时间[2009-04-07]
			"天翼",
			"金卡",
			"贵宾卡",
			"联通",
			// 以下内容增加时间[2009-03-30]
			"(港)内部", "（机）", "（玄\u2014）", "<六>－(彩)", "21世纪中国基金会", "爱国者同盟",
			"安魂网", "八老", "罢工", "罢课", "白军", "白立朴", "白梦", "办理各种", "办证", "办证上网",
			"包皮", "保钓", "保罗二世教皇", "保密室", "鲍戈", "鲍彤", "暴乱", "暴政", "北大三角地论坛",
			"北韩", "北京当局", "北京政权", "北京之春", "北美自由论坛", "贝领", "本公司代办", "\u5C44",
			"逼样", "必中", "婊子", "博讯", "不解来电", "布局十七大", "彩民法轮功", "彩民提供", "踩江",
			"蔡崇国", "蔡武", "藏独", "操逼", "操比", "操蛋", "操你", "操你妈", "操他", "曹长青",
			"曹刚川", "插你", "柴玲", "长期办理", "长期代办", "常劲", "陈炳基", "陈进", "陈军", "陈蒙",
			"陈破空", "陈水扁", "陈希同", "陈小同", "陈宣良", "陈一谘", "陈总统", "\u9673水扁",
			"成人热线", "成人书库", "诚信代办", "程凯", "程铁军", "程真", "迟浩田", "持不同政见", "赤匪",
			"赤化", "赤龙", "赤裸", "出兵", "出兵钓鱼岛", "出售答案", "初夜访谈", "喘息", "春夏自由论坛",
			"从传统人制到竞争优胜", "从胜利后撤军西藏边防", "达赖", "打I", "打倒", "打倒日本", "打倒日本鬼子",
			"打倒小日本", "打倒小日本鬼子", "打炮", "大b", "大逼", "大比", "大彩", "大参考", "大法",
			"大花逼", "大纪元", "大纪元新闻网", "大纪园", "大家论坛", "大量转发", "大陆当局", "大史", "大史记",
			"大史纪", "大中国论坛", "大中华论坛", "大众真人真事", "代办各种", "代办各种证件", "代办文凭",
			"代办证件", "戴相龙", "弹劾", "弹药", "党纪委", "登辉", "邓小平", "邓笑贫", "邓颖超日记",
			"迪里夏提", "抵制日货", "抵制日货反日游行", "抵制日货集会", "抵制日货示威", "抵制日货游行", "地下教会",
			"地下刊物", "巅峰体验", "电视流氓", "钓鱼岛", "丁关根", "丁元", "丁子霖", "东北独立", "东方红时空",
			"东南西北论谈", "东社", "东突", "东土耳其斯坦", "东西南北论坛", "东洲", "动乱", "毒品", "独裁",
			"独裁政治", "独夫", "独立台湾会", "独立中文笔会", "杜智富", "短信奖励", "多维", "屙民", "二B",
			"二逼", "发财诗", "发抡", "发抡功", "发伦", "发伦功", "发轮", "发论", "发论公", "发论功",
			"发骚", "发正念", "法*功", "法lun功", "法会", "法愣", "法抡", "法抡功", "法仑", "法伦",
			"法轮", "法轮大法", "法轮大法好", "法轮功", "法轮功洪志", "法轮真善忍", "法\u8F2A", "法论",
			"法十轮十功", "法谪", "法谪功", "反封锁技术", "反腐败论坛", "反攻", "反攻大陆", "反共", "反人类",
			"反日", "反日集会", "反日示威", "反日游行", "反日游行抵制日货", "反日游行反日游行抵制日货", "反日游行集会",
			"反日游行示威", "反社会", "贩卖迷药", "贩卖枪支", "方励之", "方舟子", "放高利贷", "放入春药",
			"飞扬论坛", "斐得勒", "废除劳教签名", "废统", "费良勇", "分家在", "分裂", "焚烧日货", "粉嫩小洞",
			"粉饰太平", "风波记", "风雨神州", "风雨神州论坛", "封从德", "封杀", "冯东海", "冯素英",
			"佛展千手法", "腐败政府", "阝月", "付申奇", "傅申奇", "傅志寰", "干你娘", "干死你", "肛交",
			"肛门", "港彩", "港科", "港料", "港密传", "港内绝密", "港内料", "高利贷", "高利息贷款",
			"高文谦", "高息贷款", "高薪养廉", "高自联", "睾丸", "戈扬", "鸽派", "个人崇拜", "各种文凭",
			"各种证件", "各种证书", "根敦?确吉尼玛", "公司快速代办", "功法", "共产党", "共党", "共\u9EE8",
			"共匪", "共狗", "共军", "狗b", "狗操", "狗卵子", "狗娘", "狗屎", "古兰经", "关卓中",
			"贯通两极法", "龟公", "龟头", "鬼子", "郭伯雄", "郭罗基", "郭平", "郭岩华", "国家安全",
			"国家机密", "国军", "国贼", "哈批", "海南私彩", "韩东方", "韩联潮", "汉风", "汉奸", "何德普",
			"何勇", "和平请愿书", "河殇", "贺国强", "黑暗小屋", "黑车", "黑枪", "嘿车", "红灯区",
			"红魂网站", "红色恐怖", "红兽", "宏法", "洪传", "洪吟", "洪哲胜", "洪志", "呼I", "胡紧掏",
			"胡锦涛", "胡锦滔", "胡锦淘", "胡景涛", "胡平", "胡耀邦", "胡总书记", "护法", "花花公子",
			"华建敏", "华通时事论坛", "华语世界论坛", "华岳时事论坛", "黄慈萍", "黄祸", "黄局", "黄菊", "黄翔",
			"回民暴动", "悔过书", "慧网", "婚介", "鸡八", "鸡巴", "鸡毛信文汇", "姬胜德", "积克馆",
			"激情大片", "激情电影", "激情视频", "激情体验", "激情主持", "集体上访", "集体做爱", "集团长期",
			"纪元", "妓女", "贾庆林", "贾廷安", "贾育台", "假币", "假钞", "假车牌", "假发票", "假户口本",
			"假身份证", "假文凭", "假证", "奸淫", "贱逼", "贱比", "贱货", "贱人", "江core", "江ze民",
			"江八点", "江蛤蟆", "江鬼", "江流氓", "江罗", "江绵恒", "江青", "江戏子", "江则民", "江泽慧",
			"江泽民", "江责民", "江\u6FA4民", "江贼", "江贼民", "江折民", "江猪", "江猪媳", "江主席",
			"姜春云", "将则民", "茳\u6FA4民", "僵贼", "僵贼民", "疆独", "蒋彦永",
			"\u8523\u5F65永", "酱猪媳", "矫情纵语", "叫床", "教养院", "揭批书", "戒严", "今码",
			"金尧如", "锦涛", "京上访", "经文", "惊暴双乳", "精液", "精子", "靖国神社", "静坐", "九成新",
			"九评共产党", "酒店急聘", "军转安置", "军转干部", "卡号抽奖", "卡务部", "开放杂志", "抗日",
			"抗日，", "抗日和鬼子的组合", "抗日集会", "抗日示威", "抗日游行", "抗议", "考后付款", "靠你妈",
			"刻章", "跨世纪的良心犯", "狂操", "邝锦文", "来京上访", "烂逼", "烂比", "烂货", "劳动教养所",
			"劳改", "劳教", "老江", "老姜", "老毛", "老人政治", "雷管", "黎安友", "李长春", "李大师",
			"李登辉", "李红痔", "李宏志", "李宏治", "李洪宽", "李洪志", "李洪志大师", "李继耐", "李兰菊",
			"李岚清", "李录", "李禄", "李鹏", "李瑞环", "李少民", "李淑娴", "李旺阳", "李文斌", "李小朋",
			"李小鹏", "李月月鸟", "李志绥", "李总理", "李总统", "里藏春", "理想信念斗争", "连胜德",
			"联合起诉最高人民法院", "联通折分", "廉政大论坛", "炼功", "梁光烈", "梁擎墩", "两岸关系",
			"两岸三地论坛", "两个中国", "廖锡龙", "了仇", "林保华", "林彪", "林长盛", "林樵清", "林慎立",
			"林昭纪念奖", "灵码", "凌锋", "领事馆抵制日货集会", "领事馆抵制日货示威", "领事馆抵制日货游行",
			"领事馆反日游行", "领事馆反日游行集会", "领事馆反日游行示威", "领事馆集会", "领事馆示威", "领事馆游行",
			"刘宾深", "刘宾雁", "刘刚", "刘国凯", "刘华清", "刘俊国", "刘凯中", "刘千石", "刘青", "刘山青",
			"刘士贤", "刘文胜", "刘晓波", "刘晓竹", "刘永川", "柳树中学", "六```合```彩", "六合",
			"六合彩", "六'''合'''彩", "六-合-彩", "六合彩公司", "六码", "六四民主运动", "六四学生运动",
			"龙虎豹", "娄义", "吕京花", "吕秀莲", "抡功", "伦功", "轮工", "轮功", "轮奸", "罗干",
			"罗礼诗", "裸干", "妈批", "麻醉枪", "马大维", "马会", "马克思", "马良骏", "马三家", "马时敏",
			"码诗", "卖逼", "卖国", "毛厕洞", "毛都露出", "毛片", "毛泽东", "毛贼东", "美国参考",
			"美国之音", "猛男强奸", "蒙独", "蒙古独立", "孟令伟", "迷魂药", "迷药", "绵恒", "民国",
			"民进党", "民联", "民意论坛", "民运", "民阵", "民猪", "民主墙", "民族矛盾", "名学府文凭",
			"明Hui", "明慧", "莫伟强", "木犀地", "木子论坛", "内部透密", "内部信息", "奶头真红", "奶子",
			"男女公关", "男女秘事", "南大自由论坛", "南华早报", "闹事", "倪育贤", "你妈的", "你娘",
			"你说我说论坛", "牛逼", "牛比", "潘国平", "盘古乐队", "抛出惊天言论", "喷你", "屁眼", "平反",
			"平码", "迫害", "祁建", "齐墨", "汽油短缺", "钱达", "钱国梁", "钱其琛", "枪", "枪手",
			"枪支", "枪支弹药", "强奸", "抢粮记", "乔石", "窃听", "窃听器", "亲美", "亲日", "钦本立",
			"秦晋", "青天白日旗", "轻舟快讯", "情妇", "情感陪护", "情色大片", "情色电影", "庆红", "去你妈的",
			"全国手机号码抽奖", "权柄", "群奸", "群体灭绝", "热比娅", "热站政论网", "人民广场游行", "人民内情真相",
			"人民真实", "人民之声论坛", "人权", "人质", "仁吉旺姆", "日本鬼子", "日领馆", "日内瓦金融", "日你",
			"日死你", "日元贷款", "肉棒", "肉洞", "肉棍", "肉体", "肉穴", "乳房", "瑞士金融大学", "三陪",
			"骚逼", "骚货", "骚穴", "色情", "傻B", "傻逼", "善恶有报", "上访", "上海帮", "上海孤儿院",
			"上网文凭", "少妇", "邵家健", "射精", "神通加持法", "沈彤", "升天", "生者和死者", "盛华仁",
			"十八摸", "十六大", "十七年", "石戈", "时代论坛", "时事论坛", "使馆抵制日货集会", "使馆抵制日货示威",
			"使馆抵制日货游行", "使馆反日游行", "使馆反日游行集会", "使馆反日游行示威", "使馆集会", "使馆示威",
			"使馆游行", "世界经济导报", "示威", "示威集会", "事实独立", "试题答案", "手榴弹", "手枪", "熟女",
			"数据中国", "双十节", "氵去车仑工力", "水扁", "司马晋", "司马璐", "司徒华", "私彩", "四川独立",
			"四级答案", "宋xx", "宋平", "宋书元", "宋祖英", "苏家屯", "苏绍智", "苏晓康", "他妈的",
			"他母亲", "台独", "台盟", "台湾独立", "台湾狗", "台湾建国运动组织", "台湾青年独立联盟", "台湾政论区",
			"台湾自由联盟", "太子党", "贪官", "汤光中", "唐柏桥", "唐捷", "陶驷驹", "讨伐", "讨伐中宣部",
			"套牌车", "特服生", "特快办", "特码", "特码玄机", "特肖", "滕文生", "提供特码",
			"体制－中长期科技规划", "天安门抵制日货集会", "天安门抵制日货示威", "天安门抵制日货游行", "天安门反日游行",
			"天安门反日游行集会", "天安门反日游行示威", "天安门集会", "天安门录影带", "天安门母亲", "天安门示威",
			"天安门示威小泉鬼子", "天安门事件", "天安门屠杀", "天安门一代", "天安门游行", "天鹅之旅", "天怒",
			"天葬", "舔奶", "同胞书", "童屹", "统独", "统独论坛", "统战", "偷欢", "偷窥", "偷录",
			"偷情", "投毒", "透码", "透特", "涂运普", "屠杀", "推翻", "退党", "外交论坛", "外交与方略",
			"外蒙", "外滩游行", "晚年周恩来", "万润南", "万维读者论坛", "万晓东", "汪岷", "亡党", "王宝森",
			"王炳章", "王策", "王超华", "王丹", "王辅臣", "王刚", "王涵万", "王沪宁", "王军涛", "王力雄",
			"王瑞林", "王润生", "王若望", "王希哲", "王秀丽", "王冶坪", "网特", "为大陆彩民提供", "围攻",
			"尉健行", "魏京生", "魏新生", "温加饱", "温家宝", "温元凯", "文凭证件", "窝囊中国", "我操",
			"我操你", "我的后讨伐中宣部时代", "我日", "我是回民", "无帮国", "无界浏览器", "无能政府", "吴百益",
			"吴邦国", "吴方城", "吴官正", "吴弘达", "吴宏达", "吴仁华", "吴学灿", "吴学璨", "吾尔开希",
			"伍凡", "西藏独立", "西藏论坛", "洗脑", "系统,日本", "下体", "先天健康法", "现在支票",
			"香港和电话的组合", "香港和港通的组合", "香港黄大仙", "香港马会", "香港明报", "香港内幕", "香港赛马会",
			"香港沈氏公司", "项怀诚", "项小吉", "小参考", "小鬼子", "小鸡鸡", "小泉", "小泉鬼子", "小日本",
			"小日本鬼子", "小穴", "肖强", "邪恶", "谢长廷", "谢选骏", "谢中之", "心藏大恶", "辛灏年",
			"新观察论坛", "新华举报", "新华内情", "新华通论坛", "新疆独立", "新生网", "新声部落", "新唐人",
			"新闻封锁", "新语丝", "性爱", "性福", "性伙伴个案考察", "性交", "性交大赛", "性交姿势", "性欲",
			"熊炎", "熊焱", "徐邦秦", "徐才厚", "徐匡迪", "徐水良", "许家屯", "玄机", "薛伟", "雪山狮子",
			"亚洲自由之声", "严家其", "严家祺", "阎明复", "燕南评论", "央视内部晚会", "杨怀安", "杨建利",
			"杨巍", "杨月清", "杨周", "姚月谦", "摇彩", "摇奖", "夜话紫禁城", "一党专政", "一党专制",
			"一切证件", "一肖", "一中一台", "伊斯兰", "义解", "亦凡", "异见人士", "异议人士", "易丹轩",
			"易志熹", "阴唇", "阴道", "阴道被捅", "阴蒂", "阴茎", "阴水", "淫荡", "淫水", "淫图",
			"淫穴", "银联通知", "尹庆民", "英语四六级答案", "鹰派", "由喜贵", "游行", "游行集会", "游行示威",
			"幼齿", "于大海", "于浩成", "余英时", "舆论反制", "宇明网", "远志明", "杂种", "昝爱宗", "赃车",
			"则民", "择民", "泽民", "贼民", "曾道人", "曾培炎", "曾庆红", "炸药", "占领钓鱼岛", "张伯笠",
			"张博涵", "张钢", "张宏堡", "张健", "张开双腿", "张林", "张万年", "张伟国", "张昭富", "张志清",
			"招妓", "赵海青", "赵南", "赵品潞", "赵式眼、",
			"赵式扬",
			"赵四眼",
			"赵晓微",
			"赵子扬",
			"赵子阳",
			"赵紫阳",
			"赵紫阳证书",
			"真善忍",
			"镇压",
			"争鸣论坛",
			"正法",
			"正见网",
			"正邪大决战",
			"正义党论坛",
			"郑义",
			"郑源",
			"政治反对派",
			"政治犯",
			"支那",
			"指点江山论坛",
			"制作证件",
			"致胡书记的公开信",
			"中俄边界",
			"中俄密约",
			"中功",
			"中共",
			"中国报禁",
			"中国泛蓝联盟",
			"中国复兴论坛",
			"中国改革年代政治斗争",
			"中国孤儿院",
			"中国科技需要根本改变",
			"中国论坛",
			"中国社会的艾滋病",
			"中国社会进步党",
			"中国社会论坛",
			"中国威胁论",
			"中国问题论坛",
			"中国真实内容",
			"中国之春",
			"中国猪",
			"中\u570B\u7576局",
			"中华大众",
			"中华讲清",
			"中华民国",
			"中华人民实话实说",
			"中华人民正邪",
			"中华时事",
			"中华养生益智功",
			"中华真实报道",
			"中苏边界争端与武装冲突",
			"中宣部",
			"钟山风雨论坛",
			"周恩来忏悔",
			"周恩来后悔",
			"周恩来自责",
			"周锋锁",
			"周天法",
			"朱嘉明",
			"朱琳",
			"朱容基",
			"朱溶剂",
			"朱\u9555基",
			"猪聋畸",
			"猪毛1",
			"专业代考",
			"转法轮",
			"子弹",
			"自焚",
			"宗教压迫",
			"走光",
			"走私",
			"走私车",
			"最骚",
			"作爱",
			"做爱经典",
			"做爱全过程",
			"做爱挑逗",
			// 2009-10-27日增加
			"反共游击队", "人权", "专制", "暴乱", "血债", "法轮功", "610办", "洪法", "示威", "民主",
			"聚集", "上访", "抵制", "枪杀", "黑枪", "自由网", "请愿", "义军", "联名信", "血渍", "造业",
			"明慧", "暴行", "法轮功信徒", "蛊惑", "消业", "正念", "魔障", "圣火", "密宗", "天目",
			"邪魔", "救度", "公道话", "统一教", "慢驶", "家族势力", "新缰人", "民运", "绝食", "学潮",
			"民怨", "天安门", "法轮大法", "六一零", "达赖", "6.4.运动", "人权侵害", "爆炸", "冲击",
			"手枪", "暗杀", "黑火药", "人权调查", "卖国贼", "高压", "颠覆", "社会不公", "魔难",
			"minghui", "酷行", "罄竹难书", "讲真相", "上层次", "结印", "善念", "噶玛巴", "堪布",
			"境界", "放光明制作中心", "清楚邪恶", "健康法", "公投", "罢驶", "庭审", "反抗", "64运动",
			"独载", "学运", "血腥", "骚乱", "大法", "FLG", "传单", "平反", "集会", "武器", "对抗",
			"子弹", "针扎", "华达通迅", "六四", "打倒", "异己", "联总", "谢万军", "救度", "正法",
			"打压", "真念", "觉者", "下法轮", "业力", "中功", "仁波切", "新生", "修炼", "正信",
			"放下执着", "真耶稣教会", "翻案", "冤案", "旭日厂", "白粉", "黑帮", "舒乐安定", "拍丸", "鸡巴",
			"北妹", "荡妇", "三公", "大庄", "设局", "波胆", "刷卡成功", "免房费名模女儿", "三级", "兽交",
			"买春", "三陪", "叫鸡", "淫荡", "赌恒指", "六合彩", "肖中特", "中特", "黑车", "走私",
			"摇头丸", "发票代开", "代理税率", "优惠税金", "票源正规", "税票代办", "税票供应", "税票办理",
			"税率低", "税金优", "税号码改13", "您好税提供", "向外代办", "司有开票", "发票代办", "发票出售",
			"EstaZolam", "Sleeping Pill", "A级", "肉棒", "肉洞", "八婆", "外围", "赔率",
			"盘口", "无聊", "信用卡", "名模尊享", "春宫", "性交", "卖春", "色情", "男妓", "激情",
			"仙灵签", "特码", "特别号码", "枪支", "套牌车", "海洛因", "毒品", "车辆手续发票", "税点优惠",
			"定税企业", "国地税", "税票代开", "税票出售", "税点低", "税率优", "税后付款", "税票据", "地下钱庄",
			"司有代票", "司有开税", "发票提供", "发票需要", "艾司唑仑", "丸仔", "肛交", "北姑", "嫩穴",
			"博彩", "对冲", "出千", "下盘", "银行卡消费问题银联管理中心", "婚外情调查", "A片", "淫乱", "口交",
			"陪睡觉", "叫小姐", "小穴", "淫水", "赌球", "曾道人", "玄机", "爆炸装置", "套牌小车", "冰毒",
			"点数优惠", "对外优惠", "税率优惠", "对外代办", "税票代理", "税票提供", "税票需要", "税点优",
			"税金低", "税查验", "你好税提供", "代理", "司有代税", "发票代理", "发票供应", "发票办理", "点数低",
			"税务代", "司有税票", "发漂", "嘌据", "余额增值", "脱衣舞", "文凭车辆", "对宗教自由的迫害",
			"大善大忍", "大法之声", "大法洪转", "证实大法", "破坏正法者", "新经文", "自然功", "菩提功",
			"宏宝大法王", "玄音大法", "破迷与正心", "世界开运动功", "大层功", "摩门教", "一贯道", "洪法",
			"亚洲自由电台", "敌对分子", "网络封锁", "平反六四", "政治变革", "以商养证", "恐怖手段", "政治诉求",
			"四无目标", "政体改造论", "四步行动", "极权专政", "中共当权者", "突破信息封锁", "对人权的侵害",
			"点数优", "你好票提供", "有票开", "发嘌", "普通票", "普通可开", "代办证件", "发票刻章车牌", "法轮",
			"李洪志", "法正乾坤", "破坏大法", "转化论", "走入邪悟者", "超高级功法", "实际神", "菩提教会",
			"金普提上师", "高层生命", "大纪元网", "空劲功", "殉道圣人", "复临安息日会", "师傅法身", "弘法",
			"自由导播", "反共", "互相勾联", "民选参政", "呼吁书", "以文养整政", "十足十恶", "绝食宣言",
			"社会民主派民运", "民运功绩论", "民主权", "中共特权阶层", "民生危机", "网络活动颠覆", "虐待人权和宗教自由",
			"税上网", "你好票提供", "票优惠联系", "漂据", "余额增值", "性服务", "上网文凭", "信息封锁",
			"转法轮", "讲清真象", "修正发", "练功点", "现世现报", "开天目", "开周天秘法", "主神教", "龙华经",
			"菩提法门", "诺世纪", "讲清真象", "中天一气功", "封圣", "观音法门", "宗教迫害", "功德圆满",
			"武装反抗", "赤党", "党禁", "参透中共", "串联聚会", "民运返家", "海外民运", "宪政", "八四纲领",
			"和平改革八位一体", "废除中共一党专政统治", "中共统治集团", "党棍官僚", "人权保障", "宇宙大法", "真善忍",
			"践踏人权", "邪恶因素", "残酷迫害", "抵制邪恶", "圆顿", "慈心佛性", "玛雅语言", "大陆教会",
			"伊扎布特", "新疆独立", "颠倒国家", "政体改造", "难属", "私家侦探", "自焚", "罢工", "集体上访",
			"鸿泰通讯", "可窃对方", "宇宙众生", "各界众生", "爱护与救度", "疯狂施暴", "救度世人", "麒麟文化",
			"五至七步功", "霍比预言", "合一共融", "偷越边境", "台人制台", "真修弟子", "废除中共", "东突",
			"东突", "罢课", "罢市", "抵制游行", "鸿宇通讯", "SIM卡", "正见网", "邪恶生命", "救度一切众生",
			"保持正念", "突破封锁", "金麒麟网", "呼喊派", "格庵遗录", "巴哈依教", "藏独", "地下律师服务点",
			"天灭中共", "一党专制", "藏独", "游行", "罢餐", "言论自由", "抵制示威", "拷贝", "专业拷贝",
			"打工者俱乐部", "告全国同胞书", "破坏奥运", "温家宝", "国家领导人", "汕头农村信用社", "家乐福游行",
			"反法游行", "维权", "雪灾西藏地震", "日本军舰", "上门安装", "重组", "平方米", "汇钱", "没磁性",
			"复制", "窃听", "外来工协会", "假冒", "国家领导", "江泽民", "反日", "高息不要", "家乐福示威",
			"反法示威", "福娃出事", "福娃火车相撞地震", "抗日", "智能", "维护权益", "未来震源", "汇款", "打入",
			"磁条坏", "侦探专家", "破解", "公民私权", "反奥运", "胡锦涛", "罗干", "汕特农信社", "赶快取款",
			"抵制奥运", "劳工", "地动仪", "保钓", "安装天线节目", "示威", "破产", "总价", "汇入", "汇款到",
			"消磁", "智能", "xu ri", "十周年", "七月二十二日法轮功", "告全国同胞", "张明双", "气功",
			"转世", "陈尧军", "12590", "恶搞", "结帐日", "短信通知", "把钱汇到", "马会", "拨968",
			"票据", "办证", "自然静坐法", "张宏堡", "真相资料", "erduoqiao", "法轮功十周年", "张三是帅哥",
			"十月飞雪", "聚会", "宗功", "610.0", "唐嘉爵", "12590.0", "I2590", "用卡消费",
			"卡上扣除", "磁条", "代开", "发票", "刘通", "办理证件", "九评", "阎庆新", "梵蒂冈",
			"er dao qiao", "722十周年法轮功", "统治", "飞雪", "报复", "轮回", "短信安全关键词测试",
			"6.26", "鸡蛋", "银行卡", "钱还没存", "磁头坏", "抽奖", "税票", "开票", "文凭", "师父评注",
			"贺信彤", "烧饼歌", "潜入潜出", "智能SIM", "易通高科", "13550515599",
			"15528236773", "接受对方所有电话", "接听对方所有任何电", "不影响对方", "枪手", "兴奋剂",
			"华天高科", "诚信制卡", "信息设备", "农运", "游行", "新疆人七月四日哀悼", "7.4中山公园",
			"香港71集会", "71游行", "香港&1集会", "旭日", "智能卡", "鸿宇通讯", "13687965626",
			"13660233260", "接听对方任何来电", "多功能SIM卡", "可知道对方通话", "抗议", "蛋白同化制剂",
			"腾飞科技", "隐私", "电话卡", "兵运", "张三是个坏蛋", "中山公园哀悼", "七月一日有游行", "香港七一游行",
			"71集会", "七一游行", "新疆", "鸿达通信", "华泰通讯", "13281257753", "13560258484",
			"接听对方所有来电", "多功能手机卡", "集会", "中天", "肽类激素", "高科信息", "提供好吗", "工运",
			"盖棺定论", "七月四日中山公园", "香港集会", "香港七一集会", "香港71游行", "七一集会", "xuri",
			"利比亚" };

	/**
	 * @Title: init
	 * @Description: 初始化处理
	 * @param @param url
	 * @param @param key
	 * @param @param id
	 * @return void
	 */
	public static void init(String uc, String pwd, String pid) {
		smsUC = uc;
		smsPWD = pwd;
		smsPID = Integer.valueOf(pid);
	}

	/**
	 * @Title: filterAndSend
	 * @Description: 过滤敏感词然后发送
	 * @param @param phone
	 * @param @param message
	 * @param @return
	 * @param @throws IOException
	 * @return String
	 */
	public static String filterAndSend(String phone, String message,
			String callbackUrl) throws IOException {
		for (String item : hiddenText) {
			message = message.replaceAll(item, "***");
		}
		return sendTo(phone, message, callbackUrl);
	}

	/**
	 * 获取随机数
	 * 
	 * @return
	 */
	private static String getRandom() {
		String rand = "";
		try {
			Service srv = new Service();
			Call call = (Call) srv.createCall();
			call.setTargetEndpointAddress(new URL(registerUrl));
			call.setOperationName("getRandom");
			call.setReturnType(XMLType.XSD_STRING);
			rand = (String) call.invoke(new Object[] {});
		} catch (ServiceException ex) {
			System.out.println("createCall:" + ex.getMessage());
		} catch (MalformedURLException ex) {
			System.out.println("setTargetEndpointAddress:" + ex.getMessage());
		} catch (RemoteException ex) {
			System.out.println("invoke:" + ex.getMessage());
		}
		return rand;
	}

	/**
	 * 设置回调地址，获取通道
	 * 
	 * @param uc
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param callbackurl
	 *            回调地址
	 * @return
	 */
	private static String setCallBackAddress(String uc, String pwd,
			String callbackurl) {
		String connid = "";
		String rand = getRandom();
		String md5pwd = Encryption.md5s(rand + pwd + pwd);
		Service srv = new Service();
		try {
			Call call = (Call) srv.createCall();
			call.setTargetEndpointAddress(new URL(registerUrl));
			call.setOperation("setCallBackAddr");
			call.addParameter("uc", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("pw", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("rand", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("url", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			connid = (String) call.invoke(new Object[] { uc, md5pwd, rand,
					callbackurl });
		} catch (ServiceException ex) {
			System.out.println("call.createCall: " + ex.getMessage());
		} catch (MalformedURLException ex) {
			System.out.println("URL: " + ex.getMessage());
		} catch (RemoteException ex) {
			System.out.println("call.invoke: " + ex.getMessage());
		}
		return connid;
	}

	/**
	 * 发送短信
	 * 
	 * @param uc
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param callees
	 *            手机号码列表，半角逗号分隔
	 * @param isReturn
	 *            是否需要阅读回执
	 * @param cont
	 *            短息内容
	 * @param msgid
	 *            第三方生成的短信的标识
	 * @param connid
	 *            通道
	 * @return
	 */
	private static String sendSMS(String uc, String pwd, String callees,
			String isReturn, String cont, int msgid, String connid) {
		String re = "";
		String rand = getRandom();
		String callee[] = callees.split(",");
		String md5pwd = Encryption.md5s(rand + pwd + pwd);
		Service srv = new Service();
		try {
			Call call = (Call) srv.createCall();
			call.setTargetEndpointAddress(new URL(sendUrl));
			call.setOperationName("sendSMS");
			call.addParameter("uc", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("pw", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("rand", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("callee", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("isreturn", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("cont", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("msgid", XMLType.XSD_INT, ParameterMode.IN);
			call.addParameter("connID", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			re = (String) call.invoke(new Object[] { uc, md5pwd, rand, callee,
					isReturn, encode(cont), msgid, connid });
		} catch (ServiceException ex) {
			System.out.println("createCall: " + ex.getMessage());
		} catch (MalformedURLException ex) {
			System.out.println("setTargetEndpointAddress: " + ex.getMessage());
		} catch (RemoteException ex) {
			System.out.println("invoke: " + ex.getMessage());
		}
		return re;
	}

	private static String encode(String s) {
		if (s == null) {
			return null;
		}
		Base64  en = new Base64();
		String result = "";
		try {
			result = en.encodeToString(s.getBytes("gbk"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Title: sendTo
	 * @Description: 发送短信
	 * @param @param phone
	 * @param @param message
	 * @param @return
	 * @param @throws IOException
	 * @return String
	 */
	public static String sendTo(String phone, String message, String callbackUrl)
			throws IOException {
		if (isNotEmpty(callbackUrl)) {
			smsCallbackUrl = callbackUrl;
		}

		String connid = setCallBackAddress(smsUC, smsPWD, smsCallbackUrl);
		String result = sendSMS(smsUC, smsPWD, phone, "0", message, smsPID,
				connid);
		logger.info("短信发送结果: " + result);
		return result;
	}
	
	
	public static boolean sendSms(String mobile,String content){
		
		if(isEmpty(mobile,content)){
			return false;
		}
		
		return true;
	}
	
	//功能名，值默认send
	protected static String cmd = "send";
	//企业id
	protected static int eprId = 142;
	//用户id
	protected static String userId = "qianlong";
	//用户密码
	protected static String pwd = "56515www";
	//int型客户端生成
	protected static int msgId = 0;
	//1表示json格式返回，2表示xml格式返回
	protected static int format = 1;
	
	/**
	 * 发送短信接口
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean sendMessage(String mobile, String content) {
		return sendMogateMessage(mobile, content);
//		boolean sendStatus = false;
//		PostMethod method = null;
//		String result = "";
//		long timestamp = System.currentTimeMillis();
//		try {
//			String key = MD5Util.md5s(eprId+userId+pwd+timestamp);
//			//过滤关键字
//			for (String item : hiddenText) {
//				content = content.replaceAll(item, "***");
//			}
//			
//			StringBuffer uri = new StringBuffer("http://client.sms10000.com/api/webservice");
//			uri.append("?cmd=").append(cmd)
//			.append("&eprId=").append(eprId)
//			.append("&userId=").append(userId)
//			.append("&key=").append(key)
//			.append("&timestamp=").append(timestamp)
//			.append("&format=").append(format)
//			.append("&mobile=").append(mobile)
//			.append("&msgId=").append(msgId)
//			.append("&content=").append(URLEncoder.encode(content,"utf-8"));
//			
//			HttpClient client = new HttpClient();
//			method = new PostMethod(uri.toString());
//			method.setRequestHeader("Content-Type",
//					"application/x-www-form-urlencoded;charset=UTF-8");
//			client.executeMethod(method);
//			if (method.getStatusCode() == HttpStatus.SC_OK) {
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(method.getResponseBodyAsStream(),
//								"utf-8"));
//				String line;
//				StringBuffer response = new StringBuffer();
//				while ((line = reader.readLine()) != null) {
//					response.append(line);
//				}
//				reader.close();
//				result = response.toString();
//				JSONObject json = JSONObject.fromObject(result);
//				
//				Iterator it = json.keys();
//				while(it.hasNext()){
//					String jsonKey = String.valueOf(it.next());
//					if("result".equals(jsonKey)){
//						if(((Integer)json.get(jsonKey)).equals(1)){
//							sendStatus = true;
//						}
//						break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(method != null){
//				method.releaseConnection();
//			}
//		}
//		return sendStatus;
	}
	
	
	


	private static String Mogate_URL = "http://61.130.7.220:8023/MWGate/wmgw.asmx/MongateCsSpSendSmsNew";

	private static String Mogate_userId = "J50597";
	//用户密码
	private static String Mogate_password = "951203";
	
	/**
	 * 发送短信接口
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean sendMogateMessage(String mobile, String content) {
		boolean sendStatus = false;
		String result = "";
		try {
			//过滤关键字
			for (String item : hiddenText) {
				content = content.replaceAll(item, "***");
			}
			String url;
				url = Mogate_URL+"?userId="+ Mogate_userId
				+ "&password=" + Mogate_password + "&pszMobis=" + mobile
				+ "&pszMsg=" + URLEncoder.encode(content, "UTF-8")
				+ "&iMobiCount=" + 1 + "&pszSubPort=*";
			
			Map<String, String> retCodeMap = MogateSMSUtil.sendMongateSMS(url);
			logger.info("梦网短信通道短信发送，返回的结果：" + retCodeMap.get("retCode") + "，返回的结果码：" + retCodeMap.get("code") +"，结果描述：" + retCodeMap.get("desc"));
			if(null!=retCodeMap&&null!=retCodeMap.get("code")&&!retCodeMap.get("code").equals("")){
				result = retCodeMap.get("code");
			}else{
				 result = "fail";
				 logger.info("梦网短信通道短信发送，返回的结果code为空");
			}
			if(result.equals("success")){
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return sendStatus;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(sendMessage("13201708840", "测试，你好！"));
//		System.out.print("result:"
//				+ sendTo("18603060631", "测试，你好！", "http://127.0.0.1"));
		// System.out.println(hiddenText.length);
		// String text =
		// "聚碳酸酯在工业邓小平领域的采用已经并不新鲜，在摩托罗拉畅销的三防机defy在后部就也采用了聚碳酸酯材料，以增加耐冲击性。而贝尔金等附件外设大厂，也一直是聚碳酸酯的常客。";
		// System.out.println(text);
		// long start = System.currentTimeMillis();
		// for (int i = 0; i<10; i++) {
		// for (String item : hiddenText) {
		// text = text.replaceAll(item, "***");
		// }
		// }
		// long end = System.currentTimeMillis();
		// System.out.println("====" + (end - start));
		// System.out.println(text);
	}

}
