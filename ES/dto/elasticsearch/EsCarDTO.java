package com.zchz.business.dto.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

//车型SKU-搜索
@Document(indexName = "#{indexConfig.prefix}zchz-car", type = "car")
public class EsCarDTO implements Serializable {

    //唯一标识
    @Id
    private Long id;
    //图片
    private String image;
    //首字母
    private String shouzimu;
    //品牌
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String pinpai;
    //车系
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String chexi;
    //销售状态
    private String xiaoshouzhuangtai;
    //车型名称
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String chexingmingcheng;
    //年款
    private String niankuan;
    //厂商指导价(元)
    private String changshanzhidaojiaYuan;
    //排量
    private String pailiang;
    //厂商
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String changshang;
    //级别
    private String jibie;
    //发动机
    private String fadongji;
    //变速箱
    private String biansuxiang;
    //长*宽*高(mm)
    private String changkuangaoMm;
    //车身结构
    private String cheshenjiegou;
    //最高车速(km/h)
    private String zuigaochesu;
    //官方0-100km/h加速(s)
    private String guanfangjiasu;
    //实测0-100km/h加速(s)
    private String shicejiasu;
    //实测100-0km/h制动(m)
    private String shicezhidong;
    //实测油耗(L/100km)
    private String shiceyouhao;
    //工信部综合油耗(L/100km)
    private String gongxinbuzongheyouhao;
    //实测离地间隙(mm)
    private String shicelidijianxiMm;
    //整车质保
    private String zhengchezhibao;
    //长度(mm)
    private String changduMm;
    //宽度(mm)
    private String kuanduMm;
    //高度(mm)
    private String gaoduMm;
    //轴距(mm)
    private String zhoujuMm;
    //前轮距(mm)
    private String qianlunjuMm;
    //后轮距(mm)
    private String houlunjuMm;
    //最小离地间隙(mm)
    private String zuixiaolidijianxiMm;
    //整备质量(kg)
    private String zhengbeizhiliangKg;
    //车门数(个)
    private String chemenshuGe;
    //座位数(个)
    private String zuoweishuGe;
    //油箱容积(L)
    private String youxiangrongjiL;
    //行李厢容积(L)
    private String xinglixiangrongjiL;
    //发动机型号
    private String fadognjixinghao;
    //排量(mL)
    private String pailiangMl;
    //排量(L)
    private String pailiangL;
    //进气形式
    private String jinqixingshi;
    //气缸排列形式
    private String qigangpailiexingshi;
    //气缸数(个)
    private String qigangshuGe;
    //每缸气门数(个)
    private String meigangqimenshuGe;
    //压缩比
    private String yasuobi;
    //配气机构
    private String peiqijigou;
    //缸径(mm)
    private String gangjingMm;
    //行程(mm)
    private String xingchengMm;
    //最大马力(Ps)
    private String zuidamaliPs;
    //最大功率(kW)
    private String zuidagonglvKw;
    //最大功率转速(rpm)
    private String zuidagonglvzhuansuRpm;
    //最大扭矩(N·m)
    private String zuidaniujuNm;
    //最大扭矩转速(rpm)
    private String zuidaniujuzhuansuRpm;
    //发动机特有技术
    private String fadongjiteyoujishu;
    //燃料形式
    private String ranliaoxingshi;
    //燃油标号
    private String ranyoubiaohao;
    //供油方式
    private String gongyoufangshi;
    //缸盖材料
    private String ganggaicailiao;
    //缸体材料
    private String gangticailiao;
    //环保标准
    private String huanbaobiaozhun;
    //简称
    private String jiancheng;
    //挡位个数
    private String dangweigeshu;
    //变速箱类型
    private String biansuxiangeixing;
    //驱动方式
    private String qidongfangshi;
    //四驱形式
    private String siquxingshi;
    //中央差速器结构
    private String zhongyangchasuqijiegou;
    //前悬架类型
    private String qianxuanjialeixing;
    //后悬架类型
    private String houxuanjialeixing;
    //助力类型
    private String zhulileixing;
    //车体结构
    private String chetijiegou;
    //前制动器类型
    private String qianzhidongqileixing;
    //后制动器类型
    private String houzhidongqileixing;
    //驻车制动类型
    private String zhuchezhidongleixing;
    //前轮胎规格
    private String qianluntaiguige;
    //后轮胎规格
    private String houluntaiguige;
    //备胎规格
    private String beitaiguige;
    //主/副驾驶座安全气囊
    private String zhufujiashizuoanquanqinang;
    //前/后排侧气囊
    private String qianhoupaiceqinang;
    //前/后排头部气囊(气帘)
    private String qianhoupaitoubuqinang;
    //膝部气囊
    private String xibuqinang;
    //胎压监测装置
    private String taiyajiancezhuangzhi;
    //零胎压继续行驶
    private String lingtaiyajixuxingshi;
    //安全带未系提示
    private String anquandaiweixitishi;
    //ISOFIX儿童座椅接口
    private String isofixertongzuoyijiekou;
    //发动机电子防盗
    private String fadongjidianzifangdao;
    //车内中控锁
    private String cheneizhongkongsuo;
    //遥控钥匙
    private String yaokongyaoshi;
    //无钥匙启动系统
    private String wuyaoshiqidongxitong;
    //无钥匙进入系统
    private String wuyaoshijinruxitong;
    //ABS防抱死
    private String absfangbaosi;
    //制动力分配(EBD/CBC等)
    private String zhidonglifenpei;
    //刹车辅助(EBA/BAS/BA等)
    private String shachefuzhu;
    //牵引力控制(ASR/TCS/TRC等)
    private String qianyinlikongzhi;
    //车身稳定控制(ESC/ESP/DSC等)
    private String cheshenwendingkongzhi;
    //上坡辅助
    private String shangpofuzhu;
    //自动驻车
    private String zidongzuche;
    //陡坡缓降
    private String doupohuanjiang;
    //可变悬架
    private String kebianxuanjia;
    //空气悬架
    private String kongqixuanjia;
    //可变转向比
    private String kebianzhuanxiangbi;
    //前桥限滑差速器/差速锁
    private String qianqiaoxiahuachasuqi;
    //中央差速器锁止功能
    private String zhongyangchasuqisuozhigongneng;
    //后桥限滑差速器/差速锁
    private String houqiaoxianhuachasuqi;
    //电动天窗
    private String diandongtianchuang;
    //全景天窗
    private String quanjingtianchuang;
    //运动外观套件
    private String yundongwaiguantaojian;
    //铝合金轮圈
    private String lvhejinlunquan;
    //电动吸合门
    private String diandongxihemen;
    //侧滑门
    private String cehuamen;
    //电动后备厢
    private String diandonghoubeixiang;
    //感应后备厢
    private String ganyinghoubeixiang;
    //车顶行李架
    private String chedingxinglijia;
    //真皮方向盘
    private String zhenpifangxiangpan;
    //方向盘调节
    private String fangxiangpantiaojie;
    //方向盘电动调节
    private String fangxiangpandiandongtiaojie;
    //多功能方向盘
    private String duogongnengfangxiangpan;
    //方向盘换挡
    private String fangxiangpanhuandang;
    //方向盘加热
    private String fangxiangpanjiare;
    //方向盘记忆
    private String fangxiangpanjiyi;
    //定速巡航
    private String dingsuxunhang;
    //前/后驻车雷达
    private String qianhouzhucheleida;
    //倒车视频影像
    private String daocheshipinyingxiang;
    //行车电脑显示屏
    private String xingchediannaoxianshiping;
    //全液晶仪表盘
    private String quanyejingyibiaopan;
    //HUD抬头数字显示
    private String hudtaitoushuzixianshi;
    //座椅材质
    private String zuoyicaizhi;
    //运动风格座椅
    private String yundongfenggezuoyi;
    //座椅高低调节
    private String zuoyigaoditiaojie;
    //腰部支撑调节
    private String yaobuzhichitiaojie;
    //肩部支撑调节
    private String jianbuzhichitiaojie;
    //主/副驾驶座电动调节
    private String zhufujiashizuodiandongtiaojie;
    //第二排靠背角度调节
    private String dierpaikaobeijiaodutiaojie;
    //第二排座椅移动
    private String dierpaizuoyiyidong;
    //后排座椅电动调节
    private String houpaizuoyidiandongtiaojie;
    //电动座椅记忆
    private String diandongzuoyijiyi;
    //前/后排座椅加热
    private String qianhoupaizuoyijiare;
    //前/后排座椅通风
    private String qianhoupaizuoyitongfeng;
    //前/后排座椅按摩
    private String qianhoupaizuoyianmo;
    //第三排座椅
    private String disanpaizuoyi;
    //后排座椅放倒方式
    private String houpaizuoyifangdaofangshi;
    //前/后中央扶手
    private String qianhouzhongyangfushou;
    //后排杯架
    private String houpaibeijia;
    //GPS导航系统
    private String gpsdaohangxitong;
    //定位互动服务
    private String dingweihudongfuw;
    //中控台彩色大屏
    private String zhongkongcaisedaping;
    //蓝牙/车载电话
    private String lanyachezaidianhua;
    //车载电视
    private String chezaidianshi;
    //后排液晶屏
    private String houpaiyejingping;
    //220V/230V电源
    private String dianyuan;
    //外接音源接口
    private String waijieyinyuanjiekou;
    //CD支持MP3/WMA
    private String cdzhichimp3wma;
    //多媒体系统
    private String duomeitixitong;
    //扬声器品牌
    private String yangshengqipinpai;
    //扬声器数量
    private String yangshengqishuliang;
    //近光灯
    private String jinguangdeng;
    //远光灯
    private String yuanguangdeng;
    //日间行车灯
    private String rijianxingchedeng;
    //自适应远近光
    private String zishiyingyuanjinguang;
    //自动头灯
    private String zidongtoudeng;
    //转向辅助灯
    private String zhuanxiangfuzhudeng;
    //转向头灯
    private String zhuanxiangtoudeng;
    //前雾灯
    private String qianwudeng;
    //大灯高度可调
    private String dadenggaoduketia;
    //大灯清洗装置
    private String dadengqingxizhuangzhi;
    //车内氛围灯
    private String chebeifenweideng;
    //前/后电动车窗
    private String qianhoudiandongchechuang;
    //车窗防夹手功能
    private String chechuangfangjiashougongneng;
    //防紫外线/隔热玻璃
    private String fangziwaixiangereboli;
    //后视镜电动调节
    private String houshijingdiandongtiaojie;
    //后视镜加热
    private String houshijingjiare;
    //内/外后视镜自动防眩目
    private String neiwaihoushijingzidongfangxuanmu;
    //后视镜电动折叠
    private String houshijingdiandongzhedie;
    //后视镜记忆
    private String houshijingjiyi;
    //后风挡遮阳帘
    private String houfengdangzheyanglian;
    //后排侧遮阳帘
    private String houpaicezheyanglian;
    //后排侧隐私玻璃
    private String houpaiceyinsiboli;
    //遮阳板化妆镜
    private String zheyangbanhuahzuangjing;
    //后雨刷
    private String houyusua;
    //感应雨刷
    private String ganyingyushua;
    //空调控制方式
    private String kongtiaokongzhifangshi;
    //后排独立空调
    private String houpaidulikongtiao;
    //后座出风口
    private String houzuochufengkou;
    //温度分区控制
    private String wendufenqukongzhi;
    //车内空气调节/花粉过滤
    private String cheneikongqitiaojiehuafenguolv;
    //车载冰箱
    private String chezaibingxiang;
    //自动泊车入位
    private String zidongbocheruwei;
    //发动机启停技术
    private String fadongjiqitingjishu;
    //并线辅助
    private String bingxianfuzhu;
    //车道偏离预警系统
    private String chedaopianliyujingxitong;
    //主动刹车/主动安全系统
    private String zhudonanquanxitong;
    //整体主动转向系统
    private String zhengtizhudongzhuanxiangxitong;
    //夜视系统
    private String yeshixitong;
    //中控液晶屏分屏显示
    private String zhongkongyejingpingfenpingxianshi;
    //自适应巡航
    private String zishiyingxunhang;
    //全景摄像头
    private String quanjingshexiangtou;
    //外观颜色
    private String waiguanyanse;
    //外观颜色码
    private String waiguanyansema;
    //内饰颜色
    private String neishiyanse;
    //内饰颜色码
    private String neishiyansema;
    //后排车门开启方式
    private String houpaichemenkaiqifangshi;
    //货箱尺寸(mm)
    private String huoxiangchicunMm;
    //最大载重质量(kg)
    private String zuidazaizhongzhiliangKg;
    //电动机总功率(kW)
    private String diandongjizonggonglvKw;
    //电动机总扭矩(N·m)
    private String diandongjizongniujuNm;
    //前电动机最大功率(kW)
    private String qiandiandongjizuidagonglvKw;
    //前电动机最大扭矩(N·m)
    private String qiandiandongjizuidaniujuNm;
    //后电动机最大功率(kW)
    private String houdiandongjizuidagonglvKw;
    //后电动机最大扭矩(N·m)
    private String houdiandongjizuidaniujuNm;
    //工信部续航里程(km)
    private String gongxinbuxuhanglichengKm;
    //电池容量(kWh)
    private String dianchirongliangKwh;
    //电池组质保
    private String dianchizuzhibao;
    //电池充电时间
    private String dianchichongdianshijian;
    //充电桩价格
    private String chongdianzhuangjiage;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShouzimu(String shouzimu) {
        this.shouzimu = shouzimu;
    }

    public String getShouzimu() {
        return this.shouzimu;
    }

    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }

    public String getPinpai() {
        return this.pinpai;
    }

    public void setChexi(String chexi) {
        this.chexi = chexi;
    }

    public String getChexi() {
        return this.chexi;
    }

    public void setXiaoshouzhuangtai(String xiaoshouzhuangtai) {
        this.xiaoshouzhuangtai = xiaoshouzhuangtai;
    }

    public String getXiaoshouzhuangtai() {
        return this.xiaoshouzhuangtai;
    }

    public void setChexingmingcheng(String chexingmingcheng) {
        this.chexingmingcheng = chexingmingcheng;
    }

    public String getChexingmingcheng() {
        return this.chexingmingcheng;
    }

    public void setNiankuan(String niankuan) {
        this.niankuan = niankuan;
    }

    public String getNiankuan() {
        return this.niankuan;
    }

    public void setChangshanzhidaojiaYuan(String changshanzhidaojiaYuan) {
        this.changshanzhidaojiaYuan = changshanzhidaojiaYuan;
    }

    public String getChangshanzhidaojiaYuan() {
        return this.changshanzhidaojiaYuan;
    }

    public void setPailiang(String pailiang) {
        this.pailiang = pailiang;
    }

    public String getPailiang() {
        return this.pailiang;
    }

    public void setChangshang(String changshang) {
        this.changshang = changshang;
    }

    public String getChangshang() {
        return this.changshang;
    }

    public void setJibie(String jibie) {
        this.jibie = jibie;
    }

    public String getJibie() {
        return this.jibie;
    }

    public void setFadongji(String fadongji) {
        this.fadongji = fadongji;
    }

    public String getFadongji() {
        return this.fadongji;
    }

    public void setBiansuxiang(String biansuxiang) {
        this.biansuxiang = biansuxiang;
    }

    public String getBiansuxiang() {
        return this.biansuxiang;
    }

    public void setChangkuangaoMm(String changkuangaoMm) {
        this.changkuangaoMm = changkuangaoMm;
    }

    public String getChangkuangaoMm() {
        return this.changkuangaoMm;
    }

    public void setCheshenjiegou(String cheshenjiegou) {
        this.cheshenjiegou = cheshenjiegou;
    }

    public String getCheshenjiegou() {
        return this.cheshenjiegou;
    }

    public void setZuigaochesu(String zuigaochesu) {
        this.zuigaochesu = zuigaochesu;
    }

    public String getZuigaochesu() {
        return this.zuigaochesu;
    }

    public void setGuanfangjiasu(String guanfangjiasu) {
        this.guanfangjiasu = guanfangjiasu;
    }

    public String getGuanfangjiasu() {
        return this.guanfangjiasu;
    }

    public void setShicejiasu(String shicejiasu) {
        this.shicejiasu = shicejiasu;
    }

    public String getShicejiasu() {
        return this.shicejiasu;
    }

    public void setShicezhidong(String shicezhidong) {
        this.shicezhidong = shicezhidong;
    }

    public String getShicezhidong() {
        return this.shicezhidong;
    }

    public void setShiceyouhao(String shiceyouhao) {
        this.shiceyouhao = shiceyouhao;
    }

    public String getShiceyouhao() {
        return this.shiceyouhao;
    }

    public void setGongxinbuzongheyouhao(String gongxinbuzongheyouhao) {
        this.gongxinbuzongheyouhao = gongxinbuzongheyouhao;
    }

    public String getGongxinbuzongheyouhao() {
        return this.gongxinbuzongheyouhao;
    }

    public void setShicelidijianxiMm(String shicelidijianxiMm) {
        this.shicelidijianxiMm = shicelidijianxiMm;
    }

    public String getShicelidijianxiMm() {
        return this.shicelidijianxiMm;
    }

    public void setZhengchezhibao(String zhengchezhibao) {
        this.zhengchezhibao = zhengchezhibao;
    }

    public String getZhengchezhibao() {
        return this.zhengchezhibao;
    }

    public void setChangduMm(String changduMm) {
        this.changduMm = changduMm;
    }

    public String getChangduMm() {
        return this.changduMm;
    }

    public void setKuanduMm(String kuanduMm) {
        this.kuanduMm = kuanduMm;
    }

    public String getKuanduMm() {
        return this.kuanduMm;
    }

    public void setGaoduMm(String gaoduMm) {
        this.gaoduMm = gaoduMm;
    }

    public String getGaoduMm() {
        return this.gaoduMm;
    }

    public void setZhoujuMm(String zhoujuMm) {
        this.zhoujuMm = zhoujuMm;
    }

    public String getZhoujuMm() {
        return this.zhoujuMm;
    }

    public void setQianlunjuMm(String qianlunjuMm) {
        this.qianlunjuMm = qianlunjuMm;
    }

    public String getQianlunjuMm() {
        return this.qianlunjuMm;
    }

    public void setHoulunjuMm(String houlunjuMm) {
        this.houlunjuMm = houlunjuMm;
    }

    public String getHoulunjuMm() {
        return this.houlunjuMm;
    }

    public void setZuixiaolidijianxiMm(String zuixiaolidijianxiMm) {
        this.zuixiaolidijianxiMm = zuixiaolidijianxiMm;
    }

    public String getZuixiaolidijianxiMm() {
        return this.zuixiaolidijianxiMm;
    }

    public void setZhengbeizhiliangKg(String zhengbeizhiliangKg) {
        this.zhengbeizhiliangKg = zhengbeizhiliangKg;
    }

    public String getZhengbeizhiliangKg() {
        return this.zhengbeizhiliangKg;
    }

    public void setChemenshuGe(String chemenshuGe) {
        this.chemenshuGe = chemenshuGe;
    }

    public String getChemenshuGe() {
        return this.chemenshuGe;
    }

    public void setZuoweishuGe(String zuoweishuGe) {
        this.zuoweishuGe = zuoweishuGe;
    }

    public String getZuoweishuGe() {
        return this.zuoweishuGe;
    }

    public void setYouxiangrongjiL(String youxiangrongjiL) {
        this.youxiangrongjiL = youxiangrongjiL;
    }

    public String getYouxiangrongjiL() {
        return this.youxiangrongjiL;
    }

    public void setXinglixiangrongjiL(String xinglixiangrongjiL) {
        this.xinglixiangrongjiL = xinglixiangrongjiL;
    }

    public String getXinglixiangrongjiL() {
        return this.xinglixiangrongjiL;
    }

    public void setFadognjixinghao(String fadognjixinghao) {
        this.fadognjixinghao = fadognjixinghao;
    }

    public String getFadognjixinghao() {
        return this.fadognjixinghao;
    }

    public void setPailiangMl(String pailiangMl) {
        this.pailiangMl = pailiangMl;
    }

    public String getPailiangMl() {
        return this.pailiangMl;
    }

    public void setPailiangL(String pailiangL) {
        this.pailiangL = pailiangL;
    }

    public String getPailiangL() {
        return this.pailiangL;
    }

    public void setJinqixingshi(String jinqixingshi) {
        this.jinqixingshi = jinqixingshi;
    }

    public String getJinqixingshi() {
        return this.jinqixingshi;
    }

    public void setQigangpailiexingshi(String qigangpailiexingshi) {
        this.qigangpailiexingshi = qigangpailiexingshi;
    }

    public String getQigangpailiexingshi() {
        return this.qigangpailiexingshi;
    }

    public void setQigangshuGe(String qigangshuGe) {
        this.qigangshuGe = qigangshuGe;
    }

    public String getQigangshuGe() {
        return this.qigangshuGe;
    }

    public void setMeigangqimenshuGe(String meigangqimenshuGe) {
        this.meigangqimenshuGe = meigangqimenshuGe;
    }

    public String getMeigangqimenshuGe() {
        return this.meigangqimenshuGe;
    }

    public void setYasuobi(String yasuobi) {
        this.yasuobi = yasuobi;
    }

    public String getYasuobi() {
        return this.yasuobi;
    }

    public void setPeiqijigou(String peiqijigou) {
        this.peiqijigou = peiqijigou;
    }

    public String getPeiqijigou() {
        return this.peiqijigou;
    }

    public void setGangjingMm(String gangjingMm) {
        this.gangjingMm = gangjingMm;
    }

    public String getGangjingMm() {
        return this.gangjingMm;
    }

    public void setXingchengMm(String xingchengMm) {
        this.xingchengMm = xingchengMm;
    }

    public String getXingchengMm() {
        return this.xingchengMm;
    }

    public void setZuidamaliPs(String zuidamaliPs) {
        this.zuidamaliPs = zuidamaliPs;
    }

    public String getZuidamaliPs() {
        return this.zuidamaliPs;
    }

    public void setZuidagonglvKw(String zuidagonglvKw) {
        this.zuidagonglvKw = zuidagonglvKw;
    }

    public String getZuidagonglvKw() {
        return this.zuidagonglvKw;
    }

    public void setZuidagonglvzhuansuRpm(String zuidagonglvzhuansuRpm) {
        this.zuidagonglvzhuansuRpm = zuidagonglvzhuansuRpm;
    }

    public String getZuidagonglvzhuansuRpm() {
        return this.zuidagonglvzhuansuRpm;
    }

    public void setZuidaniujuNm(String zuidaniujuNm) {
        this.zuidaniujuNm = zuidaniujuNm;
    }

    public String getZuidaniujuNm() {
        return this.zuidaniujuNm;
    }

    public void setZuidaniujuzhuansuRpm(String zuidaniujuzhuansuRpm) {
        this.zuidaniujuzhuansuRpm = zuidaniujuzhuansuRpm;
    }

    public String getZuidaniujuzhuansuRpm() {
        return this.zuidaniujuzhuansuRpm;
    }

    public void setFadongjiteyoujishu(String fadongjiteyoujishu) {
        this.fadongjiteyoujishu = fadongjiteyoujishu;
    }

    public String getFadongjiteyoujishu() {
        return this.fadongjiteyoujishu;
    }

    public void setRanliaoxingshi(String ranliaoxingshi) {
        this.ranliaoxingshi = ranliaoxingshi;
    }

    public String getRanliaoxingshi() {
        return this.ranliaoxingshi;
    }

    public void setRanyoubiaohao(String ranyoubiaohao) {
        this.ranyoubiaohao = ranyoubiaohao;
    }

    public String getRanyoubiaohao() {
        return this.ranyoubiaohao;
    }

    public void setGongyoufangshi(String gongyoufangshi) {
        this.gongyoufangshi = gongyoufangshi;
    }

    public String getGongyoufangshi() {
        return this.gongyoufangshi;
    }

    public void setGanggaicailiao(String ganggaicailiao) {
        this.ganggaicailiao = ganggaicailiao;
    }

    public String getGanggaicailiao() {
        return this.ganggaicailiao;
    }

    public void setGangticailiao(String gangticailiao) {
        this.gangticailiao = gangticailiao;
    }

    public String getGangticailiao() {
        return this.gangticailiao;
    }

    public void setHuanbaobiaozhun(String huanbaobiaozhun) {
        this.huanbaobiaozhun = huanbaobiaozhun;
    }

    public String getHuanbaobiaozhun() {
        return this.huanbaobiaozhun;
    }

    public void setJiancheng(String jiancheng) {
        this.jiancheng = jiancheng;
    }

    public String getJiancheng() {
        return this.jiancheng;
    }

    public void setDangweigeshu(String dangweigeshu) {
        this.dangweigeshu = dangweigeshu;
    }

    public String getDangweigeshu() {
        return this.dangweigeshu;
    }

    public void setBiansuxiangeixing(String biansuxiangeixing) {
        this.biansuxiangeixing = biansuxiangeixing;
    }

    public String getBiansuxiangeixing() {
        return this.biansuxiangeixing;
    }

    public void setQidongfangshi(String qidongfangshi) {
        this.qidongfangshi = qidongfangshi;
    }

    public String getQidongfangshi() {
        return this.qidongfangshi;
    }

    public void setSiquxingshi(String siquxingshi) {
        this.siquxingshi = siquxingshi;
    }

    public String getSiquxingshi() {
        return this.siquxingshi;
    }

    public void setZhongyangchasuqijiegou(String zhongyangchasuqijiegou) {
        this.zhongyangchasuqijiegou = zhongyangchasuqijiegou;
    }

    public String getZhongyangchasuqijiegou() {
        return this.zhongyangchasuqijiegou;
    }

    public void setQianxuanjialeixing(String qianxuanjialeixing) {
        this.qianxuanjialeixing = qianxuanjialeixing;
    }

    public String getQianxuanjialeixing() {
        return this.qianxuanjialeixing;
    }

    public void setHouxuanjialeixing(String houxuanjialeixing) {
        this.houxuanjialeixing = houxuanjialeixing;
    }

    public String getHouxuanjialeixing() {
        return this.houxuanjialeixing;
    }

    public void setZhulileixing(String zhulileixing) {
        this.zhulileixing = zhulileixing;
    }

    public String getZhulileixing() {
        return this.zhulileixing;
    }

    public void setChetijiegou(String chetijiegou) {
        this.chetijiegou = chetijiegou;
    }

    public String getChetijiegou() {
        return this.chetijiegou;
    }

    public void setQianzhidongqileixing(String qianzhidongqileixing) {
        this.qianzhidongqileixing = qianzhidongqileixing;
    }

    public String getQianzhidongqileixing() {
        return this.qianzhidongqileixing;
    }

    public void setHouzhidongqileixing(String houzhidongqileixing) {
        this.houzhidongqileixing = houzhidongqileixing;
    }

    public String getHouzhidongqileixing() {
        return this.houzhidongqileixing;
    }

    public void setZhuchezhidongleixing(String zhuchezhidongleixing) {
        this.zhuchezhidongleixing = zhuchezhidongleixing;
    }

    public String getZhuchezhidongleixing() {
        return this.zhuchezhidongleixing;
    }

    public void setQianluntaiguige(String qianluntaiguige) {
        this.qianluntaiguige = qianluntaiguige;
    }

    public String getQianluntaiguige() {
        return this.qianluntaiguige;
    }

    public void setHouluntaiguige(String houluntaiguige) {
        this.houluntaiguige = houluntaiguige;
    }

    public String getHouluntaiguige() {
        return this.houluntaiguige;
    }

    public void setBeitaiguige(String beitaiguige) {
        this.beitaiguige = beitaiguige;
    }

    public String getBeitaiguige() {
        return this.beitaiguige;
    }

    public void setZhufujiashizuoanquanqinang(String zhufujiashizuoanquanqinang) {
        this.zhufujiashizuoanquanqinang = zhufujiashizuoanquanqinang;
    }

    public String getZhufujiashizuoanquanqinang() {
        return this.zhufujiashizuoanquanqinang;
    }

    public void setQianhoupaiceqinang(String qianhoupaiceqinang) {
        this.qianhoupaiceqinang = qianhoupaiceqinang;
    }

    public String getQianhoupaiceqinang() {
        return this.qianhoupaiceqinang;
    }

    public void setQianhoupaitoubuqinang(String qianhoupaitoubuqinang) {
        this.qianhoupaitoubuqinang = qianhoupaitoubuqinang;
    }

    public String getQianhoupaitoubuqinang() {
        return this.qianhoupaitoubuqinang;
    }

    public void setXibuqinang(String xibuqinang) {
        this.xibuqinang = xibuqinang;
    }

    public String getXibuqinang() {
        return this.xibuqinang;
    }

    public void setTaiyajiancezhuangzhi(String taiyajiancezhuangzhi) {
        this.taiyajiancezhuangzhi = taiyajiancezhuangzhi;
    }

    public String getTaiyajiancezhuangzhi() {
        return this.taiyajiancezhuangzhi;
    }

    public void setLingtaiyajixuxingshi(String lingtaiyajixuxingshi) {
        this.lingtaiyajixuxingshi = lingtaiyajixuxingshi;
    }

    public String getLingtaiyajixuxingshi() {
        return this.lingtaiyajixuxingshi;
    }

    public void setAnquandaiweixitishi(String anquandaiweixitishi) {
        this.anquandaiweixitishi = anquandaiweixitishi;
    }

    public String getAnquandaiweixitishi() {
        return this.anquandaiweixitishi;
    }

    public void setIsofixertongzuoyijiekou(String isofixertongzuoyijiekou) {
        this.isofixertongzuoyijiekou = isofixertongzuoyijiekou;
    }

    public String getIsofixertongzuoyijiekou() {
        return this.isofixertongzuoyijiekou;
    }

    public void setFadongjidianzifangdao(String fadongjidianzifangdao) {
        this.fadongjidianzifangdao = fadongjidianzifangdao;
    }

    public String getFadongjidianzifangdao() {
        return this.fadongjidianzifangdao;
    }

    public void setCheneizhongkongsuo(String cheneizhongkongsuo) {
        this.cheneizhongkongsuo = cheneizhongkongsuo;
    }

    public String getCheneizhongkongsuo() {
        return this.cheneizhongkongsuo;
    }

    public void setYaokongyaoshi(String yaokongyaoshi) {
        this.yaokongyaoshi = yaokongyaoshi;
    }

    public String getYaokongyaoshi() {
        return this.yaokongyaoshi;
    }

    public void setWuyaoshiqidongxitong(String wuyaoshiqidongxitong) {
        this.wuyaoshiqidongxitong = wuyaoshiqidongxitong;
    }

    public String getWuyaoshiqidongxitong() {
        return this.wuyaoshiqidongxitong;
    }

    public void setWuyaoshijinruxitong(String wuyaoshijinruxitong) {
        this.wuyaoshijinruxitong = wuyaoshijinruxitong;
    }

    public String getWuyaoshijinruxitong() {
        return this.wuyaoshijinruxitong;
    }

    public void setAbsfangbaosi(String absfangbaosi) {
        this.absfangbaosi = absfangbaosi;
    }

    public String getAbsfangbaosi() {
        return this.absfangbaosi;
    }

    public void setZhidonglifenpei(String zhidonglifenpei) {
        this.zhidonglifenpei = zhidonglifenpei;
    }

    public String getZhidonglifenpei() {
        return this.zhidonglifenpei;
    }

    public void setShachefuzhu(String shachefuzhu) {
        this.shachefuzhu = shachefuzhu;
    }

    public String getShachefuzhu() {
        return this.shachefuzhu;
    }

    public void setQianyinlikongzhi(String qianyinlikongzhi) {
        this.qianyinlikongzhi = qianyinlikongzhi;
    }

    public String getQianyinlikongzhi() {
        return this.qianyinlikongzhi;
    }

    public void setCheshenwendingkongzhi(String cheshenwendingkongzhi) {
        this.cheshenwendingkongzhi = cheshenwendingkongzhi;
    }

    public String getCheshenwendingkongzhi() {
        return this.cheshenwendingkongzhi;
    }

    public void setShangpofuzhu(String shangpofuzhu) {
        this.shangpofuzhu = shangpofuzhu;
    }

    public String getShangpofuzhu() {
        return this.shangpofuzhu;
    }

    public void setZidongzuche(String zidongzuche) {
        this.zidongzuche = zidongzuche;
    }

    public String getZidongzuche() {
        return this.zidongzuche;
    }

    public void setDoupohuanjiang(String doupohuanjiang) {
        this.doupohuanjiang = doupohuanjiang;
    }

    public String getDoupohuanjiang() {
        return this.doupohuanjiang;
    }

    public void setKebianxuanjia(String kebianxuanjia) {
        this.kebianxuanjia = kebianxuanjia;
    }

    public String getKebianxuanjia() {
        return this.kebianxuanjia;
    }

    public void setKongqixuanjia(String kongqixuanjia) {
        this.kongqixuanjia = kongqixuanjia;
    }

    public String getKongqixuanjia() {
        return this.kongqixuanjia;
    }

    public void setKebianzhuanxiangbi(String kebianzhuanxiangbi) {
        this.kebianzhuanxiangbi = kebianzhuanxiangbi;
    }

    public String getKebianzhuanxiangbi() {
        return this.kebianzhuanxiangbi;
    }

    public void setQianqiaoxiahuachasuqi(String qianqiaoxiahuachasuqi) {
        this.qianqiaoxiahuachasuqi = qianqiaoxiahuachasuqi;
    }

    public String getQianqiaoxiahuachasuqi() {
        return this.qianqiaoxiahuachasuqi;
    }

    public void setZhongyangchasuqisuozhigongneng(String zhongyangchasuqisuozhigongneng) {
        this.zhongyangchasuqisuozhigongneng = zhongyangchasuqisuozhigongneng;
    }

    public String getZhongyangchasuqisuozhigongneng() {
        return this.zhongyangchasuqisuozhigongneng;
    }

    public void setHouqiaoxianhuachasuqi(String houqiaoxianhuachasuqi) {
        this.houqiaoxianhuachasuqi = houqiaoxianhuachasuqi;
    }

    public String getHouqiaoxianhuachasuqi() {
        return this.houqiaoxianhuachasuqi;
    }

    public void setDiandongtianchuang(String diandongtianchuang) {
        this.diandongtianchuang = diandongtianchuang;
    }

    public String getDiandongtianchuang() {
        return this.diandongtianchuang;
    }

    public void setQuanjingtianchuang(String quanjingtianchuang) {
        this.quanjingtianchuang = quanjingtianchuang;
    }

    public String getQuanjingtianchuang() {
        return this.quanjingtianchuang;
    }

    public void setYundongwaiguantaojian(String yundongwaiguantaojian) {
        this.yundongwaiguantaojian = yundongwaiguantaojian;
    }

    public String getYundongwaiguantaojian() {
        return this.yundongwaiguantaojian;
    }

    public void setLvhejinlunquan(String lvhejinlunquan) {
        this.lvhejinlunquan = lvhejinlunquan;
    }

    public String getLvhejinlunquan() {
        return this.lvhejinlunquan;
    }

    public void setDiandongxihemen(String diandongxihemen) {
        this.diandongxihemen = diandongxihemen;
    }

    public String getDiandongxihemen() {
        return this.diandongxihemen;
    }

    public void setCehuamen(String cehuamen) {
        this.cehuamen = cehuamen;
    }

    public String getCehuamen() {
        return this.cehuamen;
    }

    public void setDiandonghoubeixiang(String diandonghoubeixiang) {
        this.diandonghoubeixiang = diandonghoubeixiang;
    }

    public String getDiandonghoubeixiang() {
        return this.diandonghoubeixiang;
    }

    public void setGanyinghoubeixiang(String ganyinghoubeixiang) {
        this.ganyinghoubeixiang = ganyinghoubeixiang;
    }

    public String getGanyinghoubeixiang() {
        return this.ganyinghoubeixiang;
    }

    public void setChedingxinglijia(String chedingxinglijia) {
        this.chedingxinglijia = chedingxinglijia;
    }

    public String getChedingxinglijia() {
        return this.chedingxinglijia;
    }

    public void setZhenpifangxiangpan(String zhenpifangxiangpan) {
        this.zhenpifangxiangpan = zhenpifangxiangpan;
    }

    public String getZhenpifangxiangpan() {
        return this.zhenpifangxiangpan;
    }

    public void setFangxiangpantiaojie(String fangxiangpantiaojie) {
        this.fangxiangpantiaojie = fangxiangpantiaojie;
    }

    public String getFangxiangpantiaojie() {
        return this.fangxiangpantiaojie;
    }

    public void setFangxiangpandiandongtiaojie(String fangxiangpandiandongtiaojie) {
        this.fangxiangpandiandongtiaojie = fangxiangpandiandongtiaojie;
    }

    public String getFangxiangpandiandongtiaojie() {
        return this.fangxiangpandiandongtiaojie;
    }

    public void setDuogongnengfangxiangpan(String duogongnengfangxiangpan) {
        this.duogongnengfangxiangpan = duogongnengfangxiangpan;
    }

    public String getDuogongnengfangxiangpan() {
        return this.duogongnengfangxiangpan;
    }

    public void setFangxiangpanhuandang(String fangxiangpanhuandang) {
        this.fangxiangpanhuandang = fangxiangpanhuandang;
    }

    public String getFangxiangpanhuandang() {
        return this.fangxiangpanhuandang;
    }

    public void setFangxiangpanjiare(String fangxiangpanjiare) {
        this.fangxiangpanjiare = fangxiangpanjiare;
    }

    public String getFangxiangpanjiare() {
        return this.fangxiangpanjiare;
    }

    public void setFangxiangpanjiyi(String fangxiangpanjiyi) {
        this.fangxiangpanjiyi = fangxiangpanjiyi;
    }

    public String getFangxiangpanjiyi() {
        return this.fangxiangpanjiyi;
    }

    public void setDingsuxunhang(String dingsuxunhang) {
        this.dingsuxunhang = dingsuxunhang;
    }

    public String getDingsuxunhang() {
        return this.dingsuxunhang;
    }

    public void setQianhouzhucheleida(String qianhouzhucheleida) {
        this.qianhouzhucheleida = qianhouzhucheleida;
    }

    public String getQianhouzhucheleida() {
        return this.qianhouzhucheleida;
    }

    public void setDaocheshipinyingxiang(String daocheshipinyingxiang) {
        this.daocheshipinyingxiang = daocheshipinyingxiang;
    }

    public String getDaocheshipinyingxiang() {
        return this.daocheshipinyingxiang;
    }

    public void setXingchediannaoxianshiping(String xingchediannaoxianshiping) {
        this.xingchediannaoxianshiping = xingchediannaoxianshiping;
    }

    public String getXingchediannaoxianshiping() {
        return this.xingchediannaoxianshiping;
    }

    public void setQuanyejingyibiaopan(String quanyejingyibiaopan) {
        this.quanyejingyibiaopan = quanyejingyibiaopan;
    }

    public String getQuanyejingyibiaopan() {
        return this.quanyejingyibiaopan;
    }

    public void setHudtaitoushuzixianshi(String hudtaitoushuzixianshi) {
        this.hudtaitoushuzixianshi = hudtaitoushuzixianshi;
    }

    public String getHudtaitoushuzixianshi() {
        return this.hudtaitoushuzixianshi;
    }

    public void setZuoyicaizhi(String zuoyicaizhi) {
        this.zuoyicaizhi = zuoyicaizhi;
    }

    public String getZuoyicaizhi() {
        return this.zuoyicaizhi;
    }

    public void setYundongfenggezuoyi(String yundongfenggezuoyi) {
        this.yundongfenggezuoyi = yundongfenggezuoyi;
    }

    public String getYundongfenggezuoyi() {
        return this.yundongfenggezuoyi;
    }

    public void setZuoyigaoditiaojie(String zuoyigaoditiaojie) {
        this.zuoyigaoditiaojie = zuoyigaoditiaojie;
    }

    public String getZuoyigaoditiaojie() {
        return this.zuoyigaoditiaojie;
    }

    public void setYaobuzhichitiaojie(String yaobuzhichitiaojie) {
        this.yaobuzhichitiaojie = yaobuzhichitiaojie;
    }

    public String getYaobuzhichitiaojie() {
        return this.yaobuzhichitiaojie;
    }

    public void setJianbuzhichitiaojie(String jianbuzhichitiaojie) {
        this.jianbuzhichitiaojie = jianbuzhichitiaojie;
    }

    public String getJianbuzhichitiaojie() {
        return this.jianbuzhichitiaojie;
    }

    public void setZhufujiashizuodiandongtiaojie(String zhufujiashizuodiandongtiaojie) {
        this.zhufujiashizuodiandongtiaojie = zhufujiashizuodiandongtiaojie;
    }

    public String getZhufujiashizuodiandongtiaojie() {
        return this.zhufujiashizuodiandongtiaojie;
    }

    public void setDierpaikaobeijiaodutiaojie(String dierpaikaobeijiaodutiaojie) {
        this.dierpaikaobeijiaodutiaojie = dierpaikaobeijiaodutiaojie;
    }

    public String getDierpaikaobeijiaodutiaojie() {
        return this.dierpaikaobeijiaodutiaojie;
    }

    public void setDierpaizuoyiyidong(String dierpaizuoyiyidong) {
        this.dierpaizuoyiyidong = dierpaizuoyiyidong;
    }

    public String getDierpaizuoyiyidong() {
        return this.dierpaizuoyiyidong;
    }

    public void setHoupaizuoyidiandongtiaojie(String houpaizuoyidiandongtiaojie) {
        this.houpaizuoyidiandongtiaojie = houpaizuoyidiandongtiaojie;
    }

    public String getHoupaizuoyidiandongtiaojie() {
        return this.houpaizuoyidiandongtiaojie;
    }

    public void setDiandongzuoyijiyi(String diandongzuoyijiyi) {
        this.diandongzuoyijiyi = diandongzuoyijiyi;
    }

    public String getDiandongzuoyijiyi() {
        return this.diandongzuoyijiyi;
    }

    public void setQianhoupaizuoyijiare(String qianhoupaizuoyijiare) {
        this.qianhoupaizuoyijiare = qianhoupaizuoyijiare;
    }

    public String getQianhoupaizuoyijiare() {
        return this.qianhoupaizuoyijiare;
    }

    public void setQianhoupaizuoyitongfeng(String qianhoupaizuoyitongfeng) {
        this.qianhoupaizuoyitongfeng = qianhoupaizuoyitongfeng;
    }

    public String getQianhoupaizuoyitongfeng() {
        return this.qianhoupaizuoyitongfeng;
    }

    public void setQianhoupaizuoyianmo(String qianhoupaizuoyianmo) {
        this.qianhoupaizuoyianmo = qianhoupaizuoyianmo;
    }

    public String getQianhoupaizuoyianmo() {
        return this.qianhoupaizuoyianmo;
    }

    public void setDisanpaizuoyi(String disanpaizuoyi) {
        this.disanpaizuoyi = disanpaizuoyi;
    }

    public String getDisanpaizuoyi() {
        return this.disanpaizuoyi;
    }

    public void setHoupaizuoyifangdaofangshi(String houpaizuoyifangdaofangshi) {
        this.houpaizuoyifangdaofangshi = houpaizuoyifangdaofangshi;
    }

    public String getHoupaizuoyifangdaofangshi() {
        return this.houpaizuoyifangdaofangshi;
    }

    public void setQianhouzhongyangfushou(String qianhouzhongyangfushou) {
        this.qianhouzhongyangfushou = qianhouzhongyangfushou;
    }

    public String getQianhouzhongyangfushou() {
        return this.qianhouzhongyangfushou;
    }

    public void setHoupaibeijia(String houpaibeijia) {
        this.houpaibeijia = houpaibeijia;
    }

    public String getHoupaibeijia() {
        return this.houpaibeijia;
    }

    public void setGpsdaohangxitong(String gpsdaohangxitong) {
        this.gpsdaohangxitong = gpsdaohangxitong;
    }

    public String getGpsdaohangxitong() {
        return this.gpsdaohangxitong;
    }

    public void setDingweihudongfuw(String dingweihudongfuw) {
        this.dingweihudongfuw = dingweihudongfuw;
    }

    public String getDingweihudongfuw() {
        return this.dingweihudongfuw;
    }

    public void setZhongkongcaisedaping(String zhongkongcaisedaping) {
        this.zhongkongcaisedaping = zhongkongcaisedaping;
    }

    public String getZhongkongcaisedaping() {
        return this.zhongkongcaisedaping;
    }

    public void setLanyachezaidianhua(String lanyachezaidianhua) {
        this.lanyachezaidianhua = lanyachezaidianhua;
    }

    public String getLanyachezaidianhua() {
        return this.lanyachezaidianhua;
    }

    public void setChezaidianshi(String chezaidianshi) {
        this.chezaidianshi = chezaidianshi;
    }

    public String getChezaidianshi() {
        return this.chezaidianshi;
    }

    public void setHoupaiyejingping(String houpaiyejingping) {
        this.houpaiyejingping = houpaiyejingping;
    }

    public String getHoupaiyejingping() {
        return this.houpaiyejingping;
    }

    public void setDianyuan(String dianyuan) {
        this.dianyuan = dianyuan;
    }

    public String getDianyuan() {
        return this.dianyuan;
    }

    public void setWaijieyinyuanjiekou(String waijieyinyuanjiekou) {
        this.waijieyinyuanjiekou = waijieyinyuanjiekou;
    }

    public String getWaijieyinyuanjiekou() {
        return this.waijieyinyuanjiekou;
    }

    public void setCdzhichimp3wma(String cdzhichimp3wma) {
        this.cdzhichimp3wma = cdzhichimp3wma;
    }

    public String getCdzhichimp3wma() {
        return this.cdzhichimp3wma;
    }

    public void setDuomeitixitong(String duomeitixitong) {
        this.duomeitixitong = duomeitixitong;
    }

    public String getDuomeitixitong() {
        return this.duomeitixitong;
    }

    public void setYangshengqipinpai(String yangshengqipinpai) {
        this.yangshengqipinpai = yangshengqipinpai;
    }

    public String getYangshengqipinpai() {
        return this.yangshengqipinpai;
    }

    public void setYangshengqishuliang(String yangshengqishuliang) {
        this.yangshengqishuliang = yangshengqishuliang;
    }

    public String getYangshengqishuliang() {
        return this.yangshengqishuliang;
    }

    public void setJinguangdeng(String jinguangdeng) {
        this.jinguangdeng = jinguangdeng;
    }

    public String getJinguangdeng() {
        return this.jinguangdeng;
    }

    public void setYuanguangdeng(String yuanguangdeng) {
        this.yuanguangdeng = yuanguangdeng;
    }

    public String getYuanguangdeng() {
        return this.yuanguangdeng;
    }

    public void setRijianxingchedeng(String rijianxingchedeng) {
        this.rijianxingchedeng = rijianxingchedeng;
    }

    public String getRijianxingchedeng() {
        return this.rijianxingchedeng;
    }

    public void setZishiyingyuanjinguang(String zishiyingyuanjinguang) {
        this.zishiyingyuanjinguang = zishiyingyuanjinguang;
    }

    public String getZishiyingyuanjinguang() {
        return this.zishiyingyuanjinguang;
    }

    public void setZidongtoudeng(String zidongtoudeng) {
        this.zidongtoudeng = zidongtoudeng;
    }

    public String getZidongtoudeng() {
        return this.zidongtoudeng;
    }

    public void setZhuanxiangfuzhudeng(String zhuanxiangfuzhudeng) {
        this.zhuanxiangfuzhudeng = zhuanxiangfuzhudeng;
    }

    public String getZhuanxiangfuzhudeng() {
        return this.zhuanxiangfuzhudeng;
    }

    public void setZhuanxiangtoudeng(String zhuanxiangtoudeng) {
        this.zhuanxiangtoudeng = zhuanxiangtoudeng;
    }

    public String getZhuanxiangtoudeng() {
        return this.zhuanxiangtoudeng;
    }

    public void setQianwudeng(String qianwudeng) {
        this.qianwudeng = qianwudeng;
    }

    public String getQianwudeng() {
        return this.qianwudeng;
    }

    public void setDadenggaoduketia(String dadenggaoduketia) {
        this.dadenggaoduketia = dadenggaoduketia;
    }

    public String getDadenggaoduketia() {
        return this.dadenggaoduketia;
    }

    public void setDadengqingxizhuangzhi(String dadengqingxizhuangzhi) {
        this.dadengqingxizhuangzhi = dadengqingxizhuangzhi;
    }

    public String getDadengqingxizhuangzhi() {
        return this.dadengqingxizhuangzhi;
    }

    public void setChebeifenweideng(String chebeifenweideng) {
        this.chebeifenweideng = chebeifenweideng;
    }

    public String getChebeifenweideng() {
        return this.chebeifenweideng;
    }

    public void setQianhoudiandongchechuang(String qianhoudiandongchechuang) {
        this.qianhoudiandongchechuang = qianhoudiandongchechuang;
    }

    public String getQianhoudiandongchechuang() {
        return this.qianhoudiandongchechuang;
    }

    public void setChechuangfangjiashougongneng(String chechuangfangjiashougongneng) {
        this.chechuangfangjiashougongneng = chechuangfangjiashougongneng;
    }

    public String getChechuangfangjiashougongneng() {
        return this.chechuangfangjiashougongneng;
    }

    public void setFangziwaixiangereboli(String fangziwaixiangereboli) {
        this.fangziwaixiangereboli = fangziwaixiangereboli;
    }

    public String getFangziwaixiangereboli() {
        return this.fangziwaixiangereboli;
    }

    public void setHoushijingdiandongtiaojie(String houshijingdiandongtiaojie) {
        this.houshijingdiandongtiaojie = houshijingdiandongtiaojie;
    }

    public String getHoushijingdiandongtiaojie() {
        return this.houshijingdiandongtiaojie;
    }

    public void setHoushijingjiare(String houshijingjiare) {
        this.houshijingjiare = houshijingjiare;
    }

    public String getHoushijingjiare() {
        return this.houshijingjiare;
    }

    public void setNeiwaihoushijingzidongfangxuanmu(String neiwaihoushijingzidongfangxuanmu) {
        this.neiwaihoushijingzidongfangxuanmu = neiwaihoushijingzidongfangxuanmu;
    }

    public String getNeiwaihoushijingzidongfangxuanmu() {
        return this.neiwaihoushijingzidongfangxuanmu;
    }

    public void setHoushijingdiandongzhedie(String houshijingdiandongzhedie) {
        this.houshijingdiandongzhedie = houshijingdiandongzhedie;
    }

    public String getHoushijingdiandongzhedie() {
        return this.houshijingdiandongzhedie;
    }

    public void setHoushijingjiyi(String houshijingjiyi) {
        this.houshijingjiyi = houshijingjiyi;
    }

    public String getHoushijingjiyi() {
        return this.houshijingjiyi;
    }

    public void setHoufengdangzheyanglian(String houfengdangzheyanglian) {
        this.houfengdangzheyanglian = houfengdangzheyanglian;
    }

    public String getHoufengdangzheyanglian() {
        return this.houfengdangzheyanglian;
    }

    public void setHoupaicezheyanglian(String houpaicezheyanglian) {
        this.houpaicezheyanglian = houpaicezheyanglian;
    }

    public String getHoupaicezheyanglian() {
        return this.houpaicezheyanglian;
    }

    public void setHoupaiceyinsiboli(String houpaiceyinsiboli) {
        this.houpaiceyinsiboli = houpaiceyinsiboli;
    }

    public String getHoupaiceyinsiboli() {
        return this.houpaiceyinsiboli;
    }

    public void setZheyangbanhuahzuangjing(String zheyangbanhuahzuangjing) {
        this.zheyangbanhuahzuangjing = zheyangbanhuahzuangjing;
    }

    public String getZheyangbanhuahzuangjing() {
        return this.zheyangbanhuahzuangjing;
    }

    public void setHouyusua(String houyusua) {
        this.houyusua = houyusua;
    }

    public String getHouyusua() {
        return this.houyusua;
    }

    public void setGanyingyushua(String ganyingyushua) {
        this.ganyingyushua = ganyingyushua;
    }

    public String getGanyingyushua() {
        return this.ganyingyushua;
    }

    public void setKongtiaokongzhifangshi(String kongtiaokongzhifangshi) {
        this.kongtiaokongzhifangshi = kongtiaokongzhifangshi;
    }

    public String getKongtiaokongzhifangshi() {
        return this.kongtiaokongzhifangshi;
    }

    public void setHoupaidulikongtiao(String houpaidulikongtiao) {
        this.houpaidulikongtiao = houpaidulikongtiao;
    }

    public String getHoupaidulikongtiao() {
        return this.houpaidulikongtiao;
    }

    public void setHouzuochufengkou(String houzuochufengkou) {
        this.houzuochufengkou = houzuochufengkou;
    }

    public String getHouzuochufengkou() {
        return this.houzuochufengkou;
    }

    public void setWendufenqukongzhi(String wendufenqukongzhi) {
        this.wendufenqukongzhi = wendufenqukongzhi;
    }

    public String getWendufenqukongzhi() {
        return this.wendufenqukongzhi;
    }

    public void setCheneikongqitiaojiehuafenguolv(String cheneikongqitiaojiehuafenguolv) {
        this.cheneikongqitiaojiehuafenguolv = cheneikongqitiaojiehuafenguolv;
    }

    public String getCheneikongqitiaojiehuafenguolv() {
        return this.cheneikongqitiaojiehuafenguolv;
    }

    public void setChezaibingxiang(String chezaibingxiang) {
        this.chezaibingxiang = chezaibingxiang;
    }

    public String getChezaibingxiang() {
        return this.chezaibingxiang;
    }

    public void setZidongbocheruwei(String zidongbocheruwei) {
        this.zidongbocheruwei = zidongbocheruwei;
    }

    public String getZidongbocheruwei() {
        return this.zidongbocheruwei;
    }

    public void setFadongjiqitingjishu(String fadongjiqitingjishu) {
        this.fadongjiqitingjishu = fadongjiqitingjishu;
    }

    public String getFadongjiqitingjishu() {
        return this.fadongjiqitingjishu;
    }

    public void setBingxianfuzhu(String bingxianfuzhu) {
        this.bingxianfuzhu = bingxianfuzhu;
    }

    public String getBingxianfuzhu() {
        return this.bingxianfuzhu;
    }

    public void setChedaopianliyujingxitong(String chedaopianliyujingxitong) {
        this.chedaopianliyujingxitong = chedaopianliyujingxitong;
    }

    public String getChedaopianliyujingxitong() {
        return this.chedaopianliyujingxitong;
    }

    public void setZhudonanquanxitong(String zhudonanquanxitong) {
        this.zhudonanquanxitong = zhudonanquanxitong;
    }

    public String getZhudonanquanxitong() {
        return this.zhudonanquanxitong;
    }

    public void setZhengtizhudongzhuanxiangxitong(String zhengtizhudongzhuanxiangxitong) {
        this.zhengtizhudongzhuanxiangxitong = zhengtizhudongzhuanxiangxitong;
    }

    public String getZhengtizhudongzhuanxiangxitong() {
        return this.zhengtizhudongzhuanxiangxitong;
    }

    public void setYeshixitong(String yeshixitong) {
        this.yeshixitong = yeshixitong;
    }

    public String getYeshixitong() {
        return this.yeshixitong;
    }

    public void setZhongkongyejingpingfenpingxianshi(String zhongkongyejingpingfenpingxianshi) {
        this.zhongkongyejingpingfenpingxianshi = zhongkongyejingpingfenpingxianshi;
    }

    public String getZhongkongyejingpingfenpingxianshi() {
        return this.zhongkongyejingpingfenpingxianshi;
    }

    public void setZishiyingxunhang(String zishiyingxunhang) {
        this.zishiyingxunhang = zishiyingxunhang;
    }

    public String getZishiyingxunhang() {
        return this.zishiyingxunhang;
    }

    public void setQuanjingshexiangtou(String quanjingshexiangtou) {
        this.quanjingshexiangtou = quanjingshexiangtou;
    }

    public String getQuanjingshexiangtou() {
        return this.quanjingshexiangtou;
    }

    public void setWaiguanyanse(String waiguanyanse) {
        this.waiguanyanse = waiguanyanse;
    }

    public String getWaiguanyanse() {
        return this.waiguanyanse;
    }

    public void setWaiguanyansema(String waiguanyansema) {
        this.waiguanyansema = waiguanyansema;
    }

    public String getWaiguanyansema() {
        return this.waiguanyansema;
    }

    public void setNeishiyanse(String neishiyanse) {
        this.neishiyanse = neishiyanse;
    }

    public String getNeishiyanse() {
        return this.neishiyanse;
    }

    public void setNeishiyansema(String neishiyansema) {
        this.neishiyansema = neishiyansema;
    }

    public String getNeishiyansema() {
        return this.neishiyansema;
    }

    public void setHoupaichemenkaiqifangshi(String houpaichemenkaiqifangshi) {
        this.houpaichemenkaiqifangshi = houpaichemenkaiqifangshi;
    }

    public String getHoupaichemenkaiqifangshi() {
        return this.houpaichemenkaiqifangshi;
    }

    public void setHuoxiangchicunMm(String huoxiangchicunMm) {
        this.huoxiangchicunMm = huoxiangchicunMm;
    }

    public String getHuoxiangchicunMm() {
        return this.huoxiangchicunMm;
    }

    public void setZuidazaizhongzhiliangKg(String zuidazaizhongzhiliangKg) {
        this.zuidazaizhongzhiliangKg = zuidazaizhongzhiliangKg;
    }

    public String getZuidazaizhongzhiliangKg() {
        return this.zuidazaizhongzhiliangKg;
    }

    public void setDiandongjizonggonglvKw(String diandongjizonggonglvKw) {
        this.diandongjizonggonglvKw = diandongjizonggonglvKw;
    }

    public String getDiandongjizonggonglvKw() {
        return this.diandongjizonggonglvKw;
    }

    public void setDiandongjizongniujuNm(String diandongjizongniujuNm) {
        this.diandongjizongniujuNm = diandongjizongniujuNm;
    }

    public String getDiandongjizongniujuNm() {
        return this.diandongjizongniujuNm;
    }

    public void setQiandiandongjizuidagonglvKw(String qiandiandongjizuidagonglvKw) {
        this.qiandiandongjizuidagonglvKw = qiandiandongjizuidagonglvKw;
    }

    public String getQiandiandongjizuidagonglvKw() {
        return this.qiandiandongjizuidagonglvKw;
    }

    public void setQiandiandongjizuidaniujuNm(String qiandiandongjizuidaniujuNm) {
        this.qiandiandongjizuidaniujuNm = qiandiandongjizuidaniujuNm;
    }

    public String getQiandiandongjizuidaniujuNm() {
        return this.qiandiandongjizuidaniujuNm;
    }

    public void setHoudiandongjizuidagonglvKw(String houdiandongjizuidagonglvKw) {
        this.houdiandongjizuidagonglvKw = houdiandongjizuidagonglvKw;
    }

    public String getHoudiandongjizuidagonglvKw() {
        return this.houdiandongjizuidagonglvKw;
    }

    public void setHoudiandongjizuidaniujuNm(String houdiandongjizuidaniujuNm) {
        this.houdiandongjizuidaniujuNm = houdiandongjizuidaniujuNm;
    }

    public String getHoudiandongjizuidaniujuNm() {
        return this.houdiandongjizuidaniujuNm;
    }

    public void setGongxinbuxuhanglichengKm(String gongxinbuxuhanglichengKm) {
        this.gongxinbuxuhanglichengKm = gongxinbuxuhanglichengKm;
    }

    public String getGongxinbuxuhanglichengKm() {
        return this.gongxinbuxuhanglichengKm;
    }

    public void setDianchirongliangKwh(String dianchirongliangKwh) {
        this.dianchirongliangKwh = dianchirongliangKwh;
    }

    public String getDianchirongliangKwh() {
        return this.dianchirongliangKwh;
    }

    public void setDianchizuzhibao(String dianchizuzhibao) {
        this.dianchizuzhibao = dianchizuzhibao;
    }

    public String getDianchizuzhibao() {
        return this.dianchizuzhibao;
    }

    public void setDianchichongdianshijian(String dianchichongdianshijian) {
        this.dianchichongdianshijian = dianchichongdianshijian;
    }

    public String getDianchichongdianshijian() {
        return this.dianchichongdianshijian;
    }

    public void setChongdianzhuangjiage(String chongdianzhuangjiage) {
        this.chongdianzhuangjiage = chongdianzhuangjiage;
    }

    public String getChongdianzhuangjiage() {
        return this.chongdianzhuangjiage;
    }

}

