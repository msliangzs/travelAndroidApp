## 前言
**这个是大二的时候参加大创自学安卓写的一个小东西，乞丐级别的UI和功能，放上来图一乐~**
ps: 有问题可以联系QQ：2452302230
# 随心游系统开发背景与目标
4月7日，中国旅游研究院发布《2022年清明节假日旅游市场研究报告》，经综合测算，2022年清明假日期间全国国内旅游接待总人数4325.4万人次，同比减少61.4%；实现旅游收入82.6亿元，同比减少80.7%。国内游客满意度指数为88.8，达到历史高位水平。
疫情期间，我国实行居家隔离的疫情防控方式，线下旅游难以开展。消遣和休闲方式的减少，使得人们在解封后相约出行，迎来新一波旅游浪潮。一款既可以**查设备定位、看地图导航，又可以随手拍下身边美景**，并且可以随时随地记录旅行心得的APP满足人们的出行需求。
本款APP使用了稳定版的Android Studio 4.1.3 ，该版本的一大主题是帮助用户在使用Android Jetpack库时提高工作效率。开发者对代码编辑体验以及流性Android库的IDE集成做了许多改进。
# 随心游系统实现

<img src="https://user-images.githubusercontent.com/64625073/212050259-862d71e9-9edd-407b-b481-3923ad2073cf.png" width="500">

## 登陆界面
使用了LinearLayout编写出了登录布局，最外层是一个纵向的LinearLayout,里面包含了3行直接子元素。第一行是一个横向的LinearLayout,用于输入账号信息。第一行也是一个横向的LinearLayout,用于输入密码信息。第三行是一个登录按钮。先建立一个ActivityCollector类用于管理所有的活动，BaseActivity类作为所有活动的父类，再创建一个登录界面的活动LoginActivity继承于BaseActivity类。在LoginActivity中调用findViewById()方法分别获取到账户输入框、密码输入框以及登录按钮的实例。在登录按钮的点击事件里面对输入的账号和密码进行判断，如果账号密码正确，就认为登录成功并跳转到MainActivity主界面了。

<img src="https://user-images.githubusercontent.com/64625073/212049944-96467d94-eaff-4807-adc4-f1ce35205a6e.jpg" width="200">

## 功能模块实现

<img src="https://user-images.githubusercontent.com/64625073/212054133-693dda54-5457-49a2-bff6-081c57115be0.jpg" width="200">
<img src="https://user-images.githubusercontent.com/64625073/212054166-4ee17997-a87b-468a-95fc-669330c1ce88.jpg" width="200">

### 地图定位
首先要在百度地图官网上申请一个API KEY，并且在AndroidManifest.xml文件添加读取外部存储状态等权限声明。在MainActivity的onCreate()方法，创建了一个LocationClient的实例，LocationClient的构建函数接收一个Context参数，调用getApplicationContext()方法来获取一个全局的Context参数并传入。

### 拍照打卡
笔者是使用startActivityForResult()来启动活动的，因此拍完照后会有结果返回到onActivityResult()方法。 如果拍照成功，就可以调用BitmapFactory的decodeStream()方法将照片解析成Bitmap对象，设置在ImageView中显示出来。

### 旅行日志
布局中有一个下拉刷新用来刷新新增笔记或删除笔记导致ListView列表项的变化,底部有一个添加日志的按钮。自定义控件menu_main.xml，用来作为第二个活动DetailActivity的标题栏，包含“保存”和“删除”两种功能。
