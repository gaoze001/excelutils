package excelutils;

import excelutils.vo.*;
import org.apache.commons.beanutils.BeanUtils;
import util.ExcelUtils;
import util.ListUtils;
import util.StringUtil;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Interface {


    public JFrame frame;
    public JTabbedPane jTabbedPane;
    public JPanel xxPanel, xsPanel, xxclPanel, xsclPanel;
    private boolean DEBUG = true;
    public JTextField selectTextField, date, alarmClock_hour, alarmClock_minute;
    JFileChooser fc;
    private TrayIcon trayIcon = null;
    SystemTray tray = SystemTray.getSystemTray();

    private static java.util.List<String> minuteArr = Arrays.asList("9", "19", "29", "39", "49", "59");
    private static String[] NUMBER_STR_LIST = new String[]{"一期",
            "二期", "三期", "四期", "五期", "六期", "七期", "八期", "九期", "十期",
            "十一期", "十二期", "十三期", "十四期", "十五期", "十六期", "十七期", "十八期", "十九期", "二十期",
            "二十一期", "二十二期", "二十三期", "二十四期", "二十五期", "二十六期", "二十七期", "二十八期",
            "二十九期", "三十期", "三十一期", "三十二期", "三十三期", "三十四期", "三十五期", "三十六期"
    };



    //初始化；
    @SuppressWarnings("deprecation")
    public Interface() {
        initialize();
    }

    private void initialize() {
        jTabbedPane = new JTabbedPane();
        xxPanel = new JPanel();
        xsPanel = new JPanel();
        xxclPanel = new JPanel();
        xsclPanel = new JPanel();
        frame = new JFrame("梦幻工具箱");
//        jTabbedPane.addTab("闹铃", jPanel);
        jTabbedPane.addTab("线上流水", xxPanel);
//        jTabbedPane.addTab("线下流水", xsPanel);
//        jTabbedPane.addTab("线下处理", xxclPanel);
        jTabbedPane.addTab("线上处理", xsclPanel);
        frame.setContentPane(jTabbedPane);
        try {
            download();
            String msg = JOptionPane.showInputDialog(xxPanel,"请输入口令");
            if(!"13510203141".equals(msg)){
                System.exit(0);
            }
            initXxPanel(xxPanel);
            initXsPanel(xsPanel);
            initXxclPanel(xxclPanel);
            initXsclPanel(xsclPanel);
        } catch (Exception e) {
            System.exit(0);
            e.printStackTrace();
        }

        frame.setBounds(100, 100, 1600, 900);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() { // 窗口关闭事件
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowIconified(WindowEvent e) { // 窗口最小化事件
                frame.setVisible(false);
                miniTray();
            }
        });

    }

    private void miniTray() {

        ImageIcon trayImg = new ImageIcon(ClassLoader.getSystemResource("background.png"));// 托盘图标
        trayIcon = new TrayIcon(trayImg.getImage(), "工具箱", new PopupMenu());
        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {// 单击 1 双击 2
                    tray.remove(trayIcon);
                    frame.setVisible(true);
                    frame.setExtendedState(JFrame.NORMAL);
                    frame.toFront();
                }
            }
        });
        try {
            tray.add(trayIcon);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }

    }

    public void initXxPanel(JPanel xxJPanel) throws Exception {
        xxJPanel.setLayout(null);
        xxJPanel.setBounds(100, 100, 1500, 800);

        SqliteUtil sqliteUtil = new SqliteUtil();
        List<String> regionList = Arrays.asList("租金",
                "尾款",
                "提现",
                "在线支付手续费返还",
                "在线支付退款",
                "在线支付手续费",
                "车辆押金",
                "交付费",
                "车辆管理费",
                "平台渠道费",
                "CP 19付余额打款",
                "车务费用",
                "手工账单",
                "退库停车费");
        JComboBox jComboBoxRegion = new JComboBox(regionList.toArray());
        jComboBoxRegion.setBounds(10, 10, 180, 26);
        xxJPanel.add(jComboBoxRegion);
//        JComboBox jComboBoxArea = new JComboBox(regionList.toArray());
//        jComboBoxArea.setBounds(90, 10, 180, 26);
//        xxJPanel.add(jComboBoxArea);

        Map<String, String> regionMap = new HashMap<>();
        jComboBoxRegion.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
//                        List<RegionVo> regionAreaList = sqliteUtil.queryAreaByRegion(e.getItem().toString());
//                        regionAreaList.forEach(i -> {
//                            regionMap.put(i.getArea(), i.getRegionCode());
//                        });
//                        List<String> areaList = regionAreaList.stream().map(RegionVo::getArea).collect(Collectors.toList());
////                        jComboBoxArea.removeAllItems();
//                        areaList.forEach(x -> {
////                            jComboBoxArea.addItem(x);
//                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        Vector vect = new Vector();
        AbstractTableModel dataModel = new AbstractTableModel() {
            final String[] title = {"交易方", "车牌号", "交易金额", "备注"};

            public int getColumnCount() {
                return title.length;
            }

            public int getRowCount() {
                return vect.size();
            }

            public Object getValueAt(int row, int column) {
                if (!vect.isEmpty())
                    return ((Vector) vect.elementAt(row)).elementAt(column);
                else
                    return null;
            }

            public String getColumnName(int column) {
                return title[column];
            }

            public void setValueAt(Object value, int row, int column) {
            }

            public Class getColumnClass(int c) {
                return java.lang.String.class;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        final JButton addRegion = new JButton("上传文件");
        // Create a file chooser
        fc = new JFileChooser();

        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                return f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx");
            }

            @Override
            public String getDescription() {
                return ".xlsx";
            }
        });
        addRegion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(xxJPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
//                    LinkedList<OnlineStream> yuzOkumalarStrList = new LinkedList<OnlineStream>();
                    List<OnlineStream> onlineStreamLinkedList = new LinkedList<>();
                    try {
                        onlineStreamLinkedList = ExcelUtils.readFile(file, OnlineStream.class);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
//                    readExcelFile(file, yuzOkumalarStrList);
                    vect.removeAllElements();//初始化向量对象
                    try {
                        sqliteUtil.removeOnlineStream();
                        sqliteUtil.insertOnlineStream(onlineStreamLinkedList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    onlineStreamLinkedList.forEach(i -> {
                        Vector rec_vector = new Vector();
                        rec_vector.addElement(i.getCarUserName());
                        rec_vector.addElement(i.getCarCode());
                        rec_vector.addElement(i.getMonthRentNow());
                        rec_vector.addElement(i.getRemark());
                        vect.addElement(rec_vector);
                    });
                    dataModel.fireTableStructureChanged();
                    JOptionPane.showMessageDialog(xxJPanel,"导入成功");
                } else {
                }
            }
        });

        addRegion.setBounds(270, 10, 260, 26);
        xxJPanel.add(addRegion);

        JTable table = new JTable(dataModel);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(10, 40, 1500, 800);
        xxJPanel.add(scrollpane);
    }


    public void initXsPanel(JPanel xsJPanel) throws Exception {
    }

    public void initXxclPanel(JPanel xxclJPanel) throws Exception {
    }

    public void initXsclPanel(JPanel xsclJPanel) throws Exception {
        xsclJPanel.setLayout(null);
        xsclJPanel.setBounds(100, 100, 1500, 800);

        SqliteUtil sqliteUtil = new SqliteUtil();
        selectTextField = new JTextField();
        selectTextField.setBounds(10, 10, 180, 26);
        xsclJPanel.add(selectTextField);

        Map<String, OnlineResultVo> onlineResultVoMap = new HashMap<>();
        Vector vect = new Vector();
        AbstractTableModel dataModel = new AbstractTableModel() {
            final String[] title = {"车牌", "收款日期", "司机姓名", "应交月租金", "押金", "已交月租金", "期数", "当月租金所属月份", "租金收款方式", "备注", "其他费用", "其他费用金额", "其他费用收款日期", "其他费用收款方式", "备注1", "其他费用收款凭证", "其他费用收款凭证编号"};

            public int getColumnCount() {
                return title.length;
            }

            public int getRowCount() {
                return vect.size();
            }

            public Object getValueAt(int row, int column) {
                if (!vect.isEmpty())
                    return ((Vector) vect.elementAt(row)).elementAt(column);
                else
                    return null;
            }

            public String getColumnName(int column) {
                return title[column];
            }

            public void setValueAt(Object value, int row, int column) {
            }

            public Class getColumnClass(int c) {
                return java.lang.String.class;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        final JButton addRegion = new JButton("上传文件");

        final JButton selectBtu = new JButton("查询");
        final JButton updateBtu = new JButton("合并");
        final JButton exportResultBtu = new JButton("导出所有");
        final JButton exportFinalBtu = new JButton("导出日流水");
        // Create a file chooser
        fc = new JFileChooser();

        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                return f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx");
            }

            @Override
            public String getDescription() {
                return ".xlsx";
            }
        });
        addRegion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(xsclJPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    List<OnlineResultVo> yuzOkumalarStrList = new LinkedList<OnlineResultVo>();
                    try {
                        yuzOkumalarStrList = ExcelUtils.readFile(file, OnlineResultVo.class);
                        yuzOkumalarStrList.forEach(i->{
                            i.setShouKuanCode(i.getShouKuanCode().replace(" 00:00:00",""));
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    vect.removeAllElements();//初始化向量对象
                    try {
                        sqliteUtil.removeOnlineResult();
                        sqliteUtil.insertOnlineResult(yuzOkumalarStrList);
                        List<OnlineResultVo> yuzOkumalarStrListsql = sqliteUtil.queryAllOnlineResult();
                        yuzOkumalarStrListsql.forEach(i -> {
                            Vector rec_vector = new Vector();
                            rec_vector.addElement(i.getCarCode());
                            rec_vector.addElement(i.getShouKuanCode());
                            rec_vector.addElement(i.getCarUserName());
                            rec_vector.addElement(i.getMonthRentAll());
                            rec_vector.addElement(i.getCash());
                            rec_vector.addElement(i.getMonthRentNow());
                            rec_vector.addElement(i.getNumberStr());
                            rec_vector.addElement(i.getRentOfMonth());
                            rec_vector.addElement(i.getRentPayMethod());
                            rec_vector.addElement(i.getRemark());
                            rec_vector.addElement(i.getOtherCost());
                            rec_vector.addElement(i.getOtherCostAmount());
                            rec_vector.addElement(i.getOtherCostDate());
                            rec_vector.addElement(i.getOtherCostPayMethod());
                            rec_vector.addElement(i.getRemarkOne());
                            rec_vector.addElement(i.getOtherCostVoucher());
                            rec_vector.addElement(i.getOtherCostCode());
                            vect.addElement(rec_vector);
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    dataModel.fireTableStructureChanged();
                    JOptionPane.showMessageDialog(xsclPanel,"导入成功");
                } else {
                }
            }
        });
        selectBtu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vect.removeAllElements();//初始化向量对象
                try {
                    List<OnlineResultVo> yuzOkumalarStrListsql = sqliteUtil.queryOnlineResultForUser(selectTextField.getText());
                    yuzOkumalarStrListsql.forEach(i -> {
                        Vector rec_vector = new Vector();
                      //  rec_vector.addElement(i.getUserCode());
                        rec_vector.addElement(i.getCarCode());
                        rec_vector.addElement(i.getShouKuanCode());
                        rec_vector.addElement(i.getCarUserName());
                        rec_vector.addElement(i.getMonthRentAll());
                        rec_vector.addElement(i.getCash());
                        rec_vector.addElement(i.getMonthRentNow());
                        rec_vector.addElement(i.getNumberStr());
                        rec_vector.addElement(i.getRentOfMonth());
                        rec_vector.addElement(i.getRentPayMethod());
                        rec_vector.addElement(i.getRemark());
                        rec_vector.addElement(i.getOtherCost());
                        rec_vector.addElement(i.getOtherCostAmount());
                        rec_vector.addElement(i.getOtherCostDate());
                        rec_vector.addElement(i.getOtherCostPayMethod());
                        rec_vector.addElement(i.getRemarkOne());
                        rec_vector.addElement(i.getOtherCostVoucher());
                        rec_vector.addElement(i.getOtherCostCode());
                        vect.addElement(rec_vector);
                    });
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                dataModel.fireTableStructureChanged();
            }
        });
        updateBtu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //查询出所有司机
                    List<String> userNameList = sqliteUtil.queryAllUserName();
                    sqliteUtil.removeOnlineFinal();
                    List<OnlineResultVo> insertOnlineFinal = new ArrayList<>();
                    userNameList.forEach(i -> {
                        try {
                            String[] str = i.split(":");
                            //查询出本期入账信息
                            List<OnlineStream> ysSjVos = sqliteUtil.queryOnlineStreamForUser(str[0],str[1]);
                            if (ysSjVos.size() > 0) {
                                //总入账额
                                BigDecimal totalAmount = ysSjVos.stream().map(r -> StringUtil.isEmpty(StringUtil.nvl(r.getMonthRentNow())) ? BigDecimal.ZERO : new BigDecimal(r.getMonthRentNow())).reduce(BigDecimal.ZERO, BigDecimal::add);
                                //处理已有同名用户往期信息
                                List<OnlineResultVo> onlineResultVoListAll = sqliteUtil.queryOnlineResultForCarCode(str[0],str[1]);
                                //以车牌号分组
                                Map<String,List<OnlineResultVo>> onlineResultVoListMap = ListUtils.groupBy(onlineResultVoListAll,x->StringUtil.appendStr(StringUtil.nvl(x.getCarCode(),"qq"),StringUtil.nvl(x.getCarUserName(),"qq")));
                                //处理车牌对应信息
                                onlineResultVoListMap.forEach((k,v)->{

                                List<OnlineResultVo> onlineResultVoList = v;
                                //排序收款时间
                                onlineResultVoList.sort((t1, t2) -> t2.getShouKuanCode().compareTo(t1.getShouKuanCode()));
                                String numberMax = StringUtil.appendStr(onlineResultVoList.get(0).getNumberStr(),onlineResultVoList.get(0).getRentOfMonth());

                                for (OnlineResultVo x : onlineResultVoList) {
                                    if (StringUtil.isNotEmpty(x.getMonthRentAll())) {
                                        Map<String, List<OnlineResultVo>> onlineGroup = ListUtils.groupBy(onlineResultVoList, j->StringUtil.appendStr(j.getNumberStr(),j.getRentOfMonth()));
                                        List<OnlineResultVo> onlineResultVos = onlineGroup.get(numberMax);
                                        BigDecimal onlineTotal = onlineResultVos.stream().map(r -> StringUtil.isEmpty(StringUtil.nvl(r.getMonthRentNow()))? BigDecimal.ZERO : new BigDecimal(r.getMonthRentNow())).reduce(BigDecimal.ZERO, BigDecimal::add);
                                        //还款未超
                                        if ((onlineTotal.add(totalAmount)).compareTo(new BigDecimal(x.getMonthRentAll())) <= 0) {
                                            OnlineResultVo onlineResultVo = new OnlineResultVo();
                                            try {
                                                BeanUtils.copyProperties(onlineResultVo, onlineResultVos.get(0));
                                                onlineResultVo.setMonthRentNow(totalAmount.toPlainString());
                                                onlineResultVo.setShouKuanCode(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                                onlineResultVo.setNumberStr(onlineResultVoList.get(0).getNumberStr());
                                                onlineResultVo.setCarCode(str[0]);
                                                insertOnlineFinal.add(onlineResultVo);
                                            } catch (IllegalAccessException ex) {
                                                ex.printStackTrace();
                                            } catch (InvocationTargetException ex) {
                                                ex.printStackTrace();
                                            }
                                        } else {
                                            if (onlineTotal.compareTo(new BigDecimal(x.getMonthRentAll())) == 0) {
                                                OnlineResultVo onlineResultVo = new OnlineResultVo();
                                                try {
                                                    BeanUtils.copyProperties(onlineResultVo, onlineResultVos.get(0));
                                                    onlineResultVo.setMonthRentNow(totalAmount.toPlainString());
                                                    onlineResultVo.setShouKuanCode(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                                    for (int j = 0; j < NUMBER_STR_LIST.length; j++) {
                                                        if (onlineResultVoList.get(0).getNumberStr().equals(NUMBER_STR_LIST[j])) {
                                                            onlineResultVo.setNumberStr(NUMBER_STR_LIST[j + 1]);
                                                        }
                                                    }
                                                    String rentOfMonth = onlineResultVo.getRentOfMonth().replace(".", "-").replace("月份", "");
                                                    LocalDate now = (LocalDate.parse(rentOfMonth + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))).plusMonths(1);
                                                    onlineResultVo.setRentOfMonth(now.getYear() + "." + StringUtil.monthStr(now.getMonthValue()) + "月份");
                                                    onlineResultVo.setCarCode(str[0]);
                                                    insertOnlineFinal.add(onlineResultVo);
                                                } catch (IllegalAccessException ex) {
                                                    ex.printStackTrace();
                                                } catch (InvocationTargetException ex) {
                                                    ex.printStackTrace();
                                                }
                                            } else {
                                                OnlineResultVo onlineResultVo = new OnlineResultVo();
                                                OnlineResultVo onlineResultVoOld = new OnlineResultVo();
                                                try {
                                                    BeanUtils.copyProperties(onlineResultVo, onlineResultVos.get(0));
                                                    BeanUtils.copyProperties(onlineResultVoOld, onlineResultVos.get(0));
                                                    onlineResultVo.setMonthRentNow((onlineTotal.add(totalAmount)).subtract(new BigDecimal(x.getMonthRentAll())).toPlainString());
                                                    onlineResultVoOld.setMonthRentNow(new BigDecimal(x.getMonthRentAll()).subtract(onlineTotal).toPlainString());
                                                    onlineResultVo.setShouKuanCode(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                                    onlineResultVoOld.setShouKuanCode(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                                    for (int j = 0; j < NUMBER_STR_LIST.length; j++) {
                                                        if (onlineResultVoList.get(0).getNumberStr().equals(NUMBER_STR_LIST[j])) {
                                                            onlineResultVo.setNumberStr(NUMBER_STR_LIST[j + 1]);
                                                        }
                                                    }
                                                    String rentOfMonth = onlineResultVo.getRentOfMonth().replace(".", "-").replace("月份", "");
                                                    LocalDate now = (LocalDate.parse(rentOfMonth + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))).plusMonths(1);
                                                    onlineResultVo.setRentOfMonth(now.getYear() + "." + StringUtil.monthStr(now.getMonthValue()) + "月份");
                                                    onlineResultVo.setCarCode(str[0]);
                                                    onlineResultVoOld.setCarCode(str[0]);
                                                    insertOnlineFinal.add(onlineResultVo);
                                                    insertOnlineFinal.add(onlineResultVoOld);
                                                } catch (IllegalAccessException ex) {
                                                    ex.printStackTrace();
                                                } catch (InvocationTargetException ex) {
                                                    ex.printStackTrace();
                                                }
                                            }

                                        }
                                        break;
                                    }
                                }


                                });

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    });
                    sqliteUtil.insertOnlineFinal(insertOnlineFinal);
                    sqliteUtil.insertOnlineResult(insertOnlineFinal);
                    JOptionPane.showMessageDialog(xsclPanel,"处理成功");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        exportResultBtu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<OnlineResultVo> onlineResultVoList = sqliteUtil.queryAllOnlineResult();
                    ExcelUtils.exportFile("D://export","总表",onlineResultVoList);
                    JOptionPane.showMessageDialog(xsclPanel,"导出至D://export/总表.xlsx");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        exportFinalBtu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<OnlineFinalVo> onlineFinalVoList = sqliteUtil.queryAllOnlineFinal();
                    ExcelUtils.exportFile("D://export","流水表",onlineFinalVoList);
                    JOptionPane.showMessageDialog(xsclPanel,"导出至D://export/流水表.xlsx");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        addRegion.setBounds(270, 10, 160, 26);
        selectBtu.setBounds(440, 10, 80, 26);
        updateBtu.setBounds(530, 10, 80, 26);
        exportResultBtu.setBounds(620, 10, 120, 26);
        exportFinalBtu.setBounds(750, 10, 120, 26);
        xsclJPanel.add(addRegion);
        xsclJPanel.add(selectBtu);
        xsclJPanel.add(updateBtu);
        xsclJPanel.add(exportResultBtu);
        xsclJPanel.add(exportFinalBtu);

        JComboBox textItemNameField = new JComboBox();
        textItemNameField.setEditable(true);
        JTable table = new JTable(dataModel);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(10, 40, 1500, 800);
        xsclJPanel.add(scrollpane);
    }

    public void download(){

        try {

            URL url = new URL("http://qiniu.3x5y.com/1.txt");    // 创建URL对象

            URLConnection urlConnection = url.openConnection(); //获得连接对象

//            urlConnection.connect();// 打开到url引用资源的通信链接
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;

            httpUrlConnection.setConnectTimeout(30000);

            httpUrlConnection.setReadTimeout(30000);

            httpUrlConnection.connect();

            String code = new Integer(httpUrlConnection.getResponseCode()).toString();
            if(!"200".equals(code)){
                System.exit(0);
            }
        } catch (Exception e) {
            System.exit(0);
            e.printStackTrace();

        }

    }


}





