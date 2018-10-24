import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;

public class Arithmetic extends Application {
    private static long startDoTestTime = 0;
    private static long endDoTestTime = 0;
    private static long costTime = 0;
    private static String titleString = null;
    private static int calculateResult;
    private static float correctRate = 0;                          //答题正确率
    private static int correctAnswerNum = 0;                       //答对题目数

    private static int titleNum = 0;                                //用户输入运算题数
    private static int i = 1;
    private static String[] operators = {"+", "-", "*", "/"};      //运算符号

    private static int[] properFractionResult = new int[2];          //分数题目结果，下标为0的是分子，下标为1得是分母
    private static long factorialResult = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: white");

        ////////////////////////四则运算
        Tab fourOperate = new Tab("四则运算");
        VBox vBox = new VBox();
        vBox.setStyle("-fx-border-color: black");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        HBox explainHBox = new HBox();
        explainHBox.setMinSize(80, 100);
        explainHBox.setAlignment(Pos.CENTER);
        Label explainLabel = new Label();
        explainLabel.setText("\t这是一个关于四则运算法则的小程序\n" + "系统会根据用户输入的题目数量自动随机生成题目\n" + "\t\t(计算结果保留到小数点后两位)");
        explainLabel.setFont(new Font(20));
        explainHBox.getChildren().add(explainLabel);

        HBox titleNumHBox = new HBox();
        titleNumHBox.setMinSize(80, 50);
        titleNumHBox.setStyle("-fx-border-color: gray");
        titleNumHBox.setStyle("-fx-background-color: #dddddd");
        titleNumHBox.setAlignment(Pos.CENTER);
        titleNumHBox.setSpacing(20);
        Label titleNumlabel = new Label();
        titleNumlabel.setText("请 输 入 题 目 数 量 ：");
        titleNumlabel.setFont(new Font(16));
        TextField titleNumText = new TextField();
        titleNumText.setPrefSize(50, 20);
        Button submitBtn = new Button("确 定");
        titleNumHBox.getChildren().addAll(titleNumlabel, titleNumText, submitBtn);

        HBox titleHBox = new HBox();
        titleHBox.setMinSize(100, 50);
        titleHBox.setStyle("-fx-border-color: gray");
        titleHBox.setStyle("-fx-background-color: #dddddd");
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setSpacing(20);
        Label titleLabel = new Label();
        titleLabel.setText("请计算：");
        TextField titleText = new TextField();
        titleText.setDisable(true);
        titleLabel.setFont(new Font(16));
        TextField ansText = new TextField();
        ansText.setPrefSize(100, 20);
        Button submitAnsBtn = new Button("确定答案");
        titleHBox.getChildren().addAll(titleLabel, titleText, ansText, submitAnsBtn);

        HBox ritghAnsHBox = new HBox();
        ritghAnsHBox.setMinSize(100, 50);
        ritghAnsHBox.setStyle("-fx-border-color: gray");
        ritghAnsHBox.setStyle("-fx-background-color: #dddddd");
        ritghAnsHBox.setAlignment(Pos.CENTER);
        ritghAnsHBox.setSpacing(20);
        Label userAnsLabel = new Label();
        userAnsLabel.setText("您的答案为：");
        userAnsLabel.setFont(new Font(16));
        TextField userAnsText = new TextField();
        userAnsText.setPrefSize(100, 20);
        userAnsText.setDisable(true);
        userAnsText.minWidth(50);
        Label rightAnsLabel = new Label();
        rightAnsLabel.setText("正确答案为：");
        rightAnsLabel.setFont(new Font(16));
        TextField rightAnsText = new TextField();
        rightAnsText.setDisable(true);
        rightAnsText.setPrefSize(100, 20);
        Button nextTitleBtn = new Button("下一题");
        nextTitleBtn.setDisable(true);
        ritghAnsHBox.getChildren().addAll(userAnsLabel, userAnsText, rightAnsLabel, rightAnsText, nextTitleBtn);

        VBox judgeVBox = new VBox();
        judgeVBox.setAlignment(Pos.CENTER);
        Label rightRateLab = new Label();
        rightRateLab.setText("正确率：\t\t" + "得分:");
        rightRateLab.setFont(new Font(20));
        Label judgeRightFalse = new Label();
        judgeRightFalse.setFont(new Font(20));
        judgeVBox.getChildren().addAll(judgeRightFalse, rightRateLab);

        submitBtn.setOnMouseClicked(event -> {
            try {
                startDoTestTime = System.currentTimeMillis();
                titleNum = Integer.parseInt(titleNumText.getText().trim());
                submitBtn.setDisable(true);
                titleNumText.setDisable(true);
                randomTitle();
                titleText.setText(titleString);
                nextTitleBtn.setDisable(false);
                rightRateLab.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "要求输入正整数，请按要求输入！");
                alert.setTitle("不合法输入");
                alert.setHeaderText("");
                alert.show();
            }
        });
        submitAnsBtn.setOnMouseClicked(event -> {//////////////////////////////////////////////////////////////////////////////
            if (submitBtn.isDisabled()) {
                try {
                    submitAnsBtn.setDisable(true);
                    ansText.setDisable(true);
                    int temp = (Integer.parseInt(ansText.getText().trim()));
                    userAnsText.setText(temp + "");
                    rightAnsText.setText(calculateResult + "");
                    if (temp == calculateResult) {
                        judgeRightFalse.setText("恭喜你，答对了！");
                        correctAnswerNum++;
                        correctRate = (float) correctAnswerNum / titleNum * 100;
                        rightRateLab.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
                    } else {
                        judgeRightFalse.setText("答错了，细心答题正确率更高哦！");
                        rightRateLab.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
                    }
                    nextTitleBtn.setDisable(false);
                } catch (Exception e) {
                    submitAnsBtn.setDisable(false);
                    ansText.setDisable(false);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "输入内容不合法(内容不能为空、不能有字母等)！");
                    alert.setTitle("不合法输入！");
                    alert.setHeaderText("");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "请先输入题目数并确定！");
                alert.setTitle("不合法操作！");
                alert.setHeaderText("");
                alert.show();
            }
        });
        nextTitleBtn.setOnMouseClicked(event -> {
            if (!submitAnsBtn.isDisabled()) {
                if (i < titleNum) {
                    Alert noAnsTitle = new Alert(Alert.AlertType.CONFIRMATION, "你确定跳过这一题吗？");
                    noAnsTitle.setTitle("跳过这一题");
                    noAnsTitle.setHeaderText("");
                    Optional<ButtonType> result = noAnsTitle.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        //跳过这一题
                        randomTitle();
                        i++;
                        titleText.setText(titleString);
                        ansText.setText("");
                    }
                } else {
                    ansAll();
                    rightRateLab.setText("正确率：" + correctRate + "%" + "\t得分:" + correctAnswerNum * 10 + "\t耗时：" + costTime + "s");
                    startDoTestTime = 0;
                    correctAnswerNum = 0;
                    correctRate = 0;
                    endDoTestTime = 0;
                    costTime = 0;
                    nextTitleBtn.setDisable(true);
                }
            } else {
                if (i < titleNum) {
                    randomTitle();
                    i++;
                    titleText.setText(titleString);
                    ansText.setText("");
                    ansText.setDisable(false);
                    submitAnsBtn.setDisable(false);
                    userAnsText.setText("");
                    rightAnsText.setText("");
                    judgeRightFalse.setText("");
                } else {
                    ansAll();
                    nextTitleBtn.setDisable(true);
                    rightRateLab.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10 + "\t耗时：" + costTime + "s");
                    startDoTestTime = 0;
                    correctAnswerNum = 0;
                    correctRate = 0;
                    endDoTestTime = 0;
                    costTime = 0;
                }
            }
        });
        vBox.getChildren().addAll(explainHBox, titleNumHBox, titleHBox, ritghAnsHBox, judgeVBox);
        fourOperate.setContent(vBox);

        ////////////////////////分数运算
        Tab properFraction = new Tab("分数运算");
        VBox fractionVBox = new VBox();
        fractionVBox.setStyle("-fx-border-color: black");
        fractionVBox.setSpacing(10);
        fractionVBox.setAlignment(Pos.CENTER);

        HBox requireHBox = new HBox();
        requireHBox.setAlignment(Pos.CENTER);
        requireHBox.setSpacing(20);
        Label requireLabel = new Label();
        requireLabel.setText("真分数运算，要求约到最简。");
        requireLabel.setFont(Font.font(20));
        requireHBox.getChildren().add(requireLabel);

        HBox fractionNumHBox = new HBox();
        fractionNumHBox.setMinSize(100, 50);
        fractionNumHBox.setStyle("-fx-border-color: gray");
        fractionNumHBox.setStyle("-fx-background-color: #dddddd");
        fractionNumHBox.setAlignment(Pos.CENTER);
        fractionNumHBox.setSpacing(20);
        Label fractionNumLabel = new Label();
        fractionNumLabel.setText("请输入题目数：");
        fractionNumLabel.setFont(Font.font(18));
        TextField fractionNumText = new TextField();
        fractionNumText.setPrefSize(50, 20);
        Button fractionSubNumBtn = new Button("确认");
        fractionNumHBox.getChildren().addAll(fractionNumLabel, fractionNumText, fractionSubNumBtn);

        HBox fractionTitleHBox = new HBox();
        fractionTitleHBox.setMinSize(100, 50);
        fractionTitleHBox.setStyle("-fx-border-color: gray");
        fractionTitleHBox.setStyle("-fx-background-color: #dddddd");
        fractionTitleHBox.setAlignment(Pos.CENTER);
        fractionTitleHBox.setSpacing(20);
        Label fractionTitleLabel = new Label();
        fractionTitleLabel.setText("请计算：");
        TextField fractionTitleText = new TextField();
        fractionTitleText.setDisable(true);
        fractionTitleText.setPrefSize(250, 20);
        fractionTitleLabel.setFont(new Font(16));
        TextField fractionAnsText = new TextField();
        fractionAnsText.setPrefSize(150, 20);
        Button fractionSubmitAnsBtn = new Button("确定答案");
        fractionTitleHBox.getChildren().addAll(fractionTitleLabel, fractionTitleText, fractionAnsText, fractionSubmitAnsBtn);

        HBox fractionRitghAnsHBox = new HBox();
        fractionRitghAnsHBox.setMinSize(100, 50);
        fractionRitghAnsHBox.setStyle("-fx-border-color: gray");
        fractionRitghAnsHBox.setStyle("-fx-background-color: #dddddd");
        fractionRitghAnsHBox.setAlignment(Pos.CENTER);
        fractionRitghAnsHBox.setSpacing(20);
        Label fractionUserAnsLabel = new Label();
        fractionUserAnsLabel.setText("您的答案为：");
        fractionUserAnsLabel.setFont(new Font(16));
        TextField fractionUserAnsText = new TextField();
        fractionUserAnsText.setPrefSize(150, 20);
        fractionUserAnsText.setDisable(true);
        fractionUserAnsText.minWidth(50);
        Label fractionRightAnsLabel = new Label();
        fractionRightAnsLabel.setText("正确答案为：");
        fractionRightAnsLabel.setFont(new Font(16));
        TextField fractionRightAnsText = new TextField();
        fractionRightAnsText.setDisable(true);
        fractionRightAnsText.setPrefSize(150, 20);
        Button fractionNextTitleBtn = new Button("下一题");
        fractionNextTitleBtn.setDisable(true);
        fractionRitghAnsHBox.getChildren().addAll(fractionUserAnsLabel, fractionUserAnsText, fractionRightAnsLabel, fractionRightAnsText, fractionNextTitleBtn);

        VBox fractionJudgeVBox = new VBox();
        fractionJudgeVBox.setAlignment(Pos.CENTER);
        Label fractionRightRate = new Label();
        fractionRightRate.setText("正确率：\t\t" + "得分:");
        fractionRightRate.setFont(new Font(20));
        Label fractionjudgeTF = new Label();
        fractionjudgeTF.setFont(new Font(20));
        fractionJudgeVBox.getChildren().addAll(fractionjudgeTF, fractionRightRate);

        fractionSubNumBtn.setOnMouseClicked(event -> {
            try {
                startDoTestTime = System.currentTimeMillis();
                titleNum = Integer.parseInt(fractionNumText.getText().trim());
                fractionSubNumBtn.setDisable(true);
                fractionNumText.setDisable(true);
                randomProperFraction();
                fractionTitleText.setText(titleString);
                fractionNextTitleBtn.setDisable(false);
                fractionRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "要求输入正整数，请按要求输入！");
                alert.setTitle("不合法输入");
                alert.setHeaderText("");
                alert.show();
            }
        });
        fractionSubmitAnsBtn.setOnMouseClicked(event -> {
            if (fractionSubNumBtn.isDisabled()) {//判断是否输入题目数
                if (!fractionAnsText.getText().trim().equals("")) {
                    fractionSubmitAnsBtn.setDisable(true);
                    fractionAnsText.setDisable(true);
                    String temp = fractionAnsText.getText().trim();
                    fractionUserAnsText.setText(temp);
                    fractionRightAnsText.setText(properFractionResult[0] + "/" + properFractionResult[1]);
                    if (temp.equals(fractionRightAnsText.getText())) {
                        fractionjudgeTF.setText("恭喜你，答对了！");
                        correctAnswerNum++;
                        correctRate = (float) correctAnswerNum / titleNum * 100;
                        fractionRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
                    } else {
                        fractionjudgeTF.setText("答错了，细心答题正确率更高哦！");
                        fractionRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
                    }
                    fractionNextTitleBtn.setDisable(false);
                } else {
                    fractionSubmitAnsBtn.setDisable(false);
                    fractionAnsText.setDisable(false);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "输入内容不合法(内容不能为空等)！");
                    alert.setTitle("不合法输入！");
                    alert.setHeaderText("");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "请先输入题目数并点击确定按钮！");
                alert.setTitle("不合法操作！");
                alert.setHeaderText("");
                alert.show();
            }
        });
        fractionNextTitleBtn.setOnMouseClicked(event -> {
            if (!fractionSubmitAnsBtn.isDisabled()) {
                if (i < titleNum) {
                    Alert noAnsTitle = new Alert(Alert.AlertType.CONFIRMATION, "你确定跳过这一题吗？");
                    noAnsTitle.setTitle("跳过这一题");
                    noAnsTitle.setHeaderText("");
                    Optional<ButtonType> result = noAnsTitle.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        //跳过这一题
                        fractionAnsText.setText("");
                        randomProperFraction();
                        i++;
                        fractionTitleText.setText(titleString);
                    }
                } else {
                    ansAll();
                    fractionNextTitleBtn.setDisable(true);
                    fractionRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10 + "\t耗时：" + costTime + "s");
                    startDoTestTime = 0;
                    correctAnswerNum = 0;
                    correctRate = 0;
                    endDoTestTime = 0;
                    costTime = 0;
                }
            } else {
                if (i < titleNum) {
                    randomProperFraction();
                    i++;
                    fractionTitleText.setText(titleString);
                    fractionAnsText.setText("");
                    fractionAnsText.setDisable(false);
                    fractionSubmitAnsBtn.setDisable(false);
                    fractionUserAnsText.setText("");
                    fractionRightAnsText.setText("");
                    fractionjudgeTF.setText("");
                } else {
                    ansAll();
                    fractionNextTitleBtn.setDisable(true);
                    fractionRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10 + "\t耗时：" + costTime + "s");
                    startDoTestTime = 0;
                    correctAnswerNum = 0;
                    correctRate = 0;
                    endDoTestTime = 0;
                    costTime = 0;
                }
            }
        });

        fractionVBox.getChildren().addAll(requireHBox, fractionNumHBox, fractionTitleHBox, fractionRitghAnsHBox, fractionJudgeVBox);
        properFraction.setContent(fractionVBox);

        ////////////////////////阶乘运算
        Tab factorialOperate = new Tab("阶乘运算");

        VBox factorialVBox = new VBox();
        factorialVBox.setStyle("-fx-border-color: black");
        factorialVBox.setAlignment(Pos.CENTER);
        factorialVBox.setSpacing(10);
        factorialOperate.setContent(factorialVBox);

        HBox factorialRequireHBox = new HBox();
        factorialRequireHBox.setAlignment(Pos.CENTER);
        factorialRequireHBox.setSpacing(20);
        Label factorialRequireLabel = new Label();
        factorialRequireLabel.setText("阶乘运算。\n\n");
        factorialRequireLabel.setFont(Font.font(20));
        factorialRequireHBox.getChildren().add(factorialRequireLabel);

        HBox factorialNumHBox = new HBox();
        factorialNumHBox.setMinSize(100, 50);
        factorialNumHBox.setStyle("-fx-border-color: gray");
        factorialNumHBox.setStyle("-fx-background-color: #dddddd");
        factorialNumHBox.setAlignment(Pos.CENTER);
        factorialNumHBox.setSpacing(20);
        Label factorialNumLabel = new Label();
        factorialNumLabel.setText("请输入题目数：");
        factorialNumLabel.setFont(Font.font(18));
        TextField factorialNumText = new TextField();
        factorialNumText.setPrefSize(50, 20);
        Button factorialSubNumBtn = new Button("确认");
        factorialNumHBox.getChildren().addAll(factorialNumLabel, factorialNumText, factorialSubNumBtn);

        HBox factorialTitleHBox = new HBox();
        factorialTitleHBox.setMinSize(100, 50);
        factorialTitleHBox.setStyle("-fx-border-color: gray");
        factorialTitleHBox.setStyle("-fx-background-color: #dddddd");
        factorialTitleHBox.setAlignment(Pos.CENTER);
        factorialTitleHBox.setSpacing(20);
        Label factorialTitleLabel = new Label();
        factorialTitleLabel.setText("请计算：");
        TextField factorialTitleText = new TextField();
        factorialTitleText.setPrefSize(80, 20);
        factorialTitleText.setDisable(true);
        factorialTitleLabel.setFont(new Font(16));
        TextField factorialAnsText = new TextField();
        factorialAnsText.setPrefSize(200, 20);
        Button factorialSubmitAnsBtn = new Button("确定答案");
        factorialTitleHBox.getChildren().addAll(factorialTitleLabel, factorialTitleText, factorialAnsText, factorialSubmitAnsBtn);

        HBox factorialAnsHBox = new HBox();
        factorialAnsHBox.setMinSize(100, 50);
        factorialAnsHBox.setStyle("-fx-border-color: gray");
        factorialAnsHBox.setStyle("-fx-background-color: #dddddd");
        factorialAnsHBox.setAlignment(Pos.CENTER);
        factorialAnsHBox.setSpacing(20);
        Label factorialUserAnsLabel = new Label();
        factorialUserAnsLabel.setText("您的答案为：");
        factorialUserAnsLabel.setFont(new Font(16));
        TextField factorialUserAnsText = new TextField();
        factorialUserAnsText.setPrefSize(200, 20);
        factorialUserAnsText.setDisable(true);
        factorialUserAnsText.minWidth(50);
        Label factorialRightAnsLabel = new Label();
        factorialRightAnsLabel.setText("正确答案为：");
        factorialRightAnsLabel.setFont(new Font(16));
        TextField factorialRightAnsText = new TextField();
        factorialRightAnsText.setPrefSize(200, 20);
        factorialRightAnsText.setDisable(true);
        Button factorialNextTitleBtn = new Button("下一题");
        factorialNextTitleBtn.setDisable(true);
        factorialAnsHBox.getChildren().addAll(factorialUserAnsLabel, factorialUserAnsText, factorialRightAnsLabel, factorialRightAnsText, factorialNextTitleBtn);

        VBox factorialJudgeVBox = new VBox();
        factorialJudgeVBox.setAlignment(Pos.CENTER);
        Label factorialRightRate = new Label();
        factorialRightRate.setText("正确率：\t\t" + "得分:");
        factorialRightRate.setFont(new Font(20));
        Label factorialjudgeTF = new Label();
        factorialjudgeTF.setFont(new Font(20));
        factorialJudgeVBox.getChildren().addAll(factorialjudgeTF, factorialRightRate);

        factorialSubNumBtn.setOnMouseClicked(event -> {
            try {
                startDoTestTime = System.currentTimeMillis();
                titleNum = Integer.parseInt(factorialNumText.getText().trim());
                factorialSubNumBtn.setDisable(true);
                factorialNumText.setDisable(true);
                factorialRandTitle();
                factorialTitleText.setText(titleString);
                factorialNextTitleBtn.setDisable(false);
                factorialRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "要求输入正整数，请按要求输入！");
                alert.setTitle("不合法输入");
                alert.setHeaderText("");
                alert.show();
            }
        });
        factorialSubmitAnsBtn.setOnMouseClicked(event -> {//////////////////////////////////////////////////////////////////////////////
            if (factorialSubNumBtn.isDisabled()) {
                try {
                    factorialSubmitAnsBtn.setDisable(true);
                    factorialAnsText.setDisable(true);
                    long temp = (Long.parseLong(factorialAnsText.getText().trim()));
                    factorialUserAnsText.setText(temp + "");
                    factorialRightAnsText.setText(factorialResult + "");
                    if (temp == factorialResult) {
                        factorialjudgeTF.setText("恭喜你，答对了！");
                        correctAnswerNum++;
                        correctRate = (float) correctAnswerNum / titleNum * 100;
                        factorialRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
                    } else {
                        factorialjudgeTF.setText("答错了，细心答题正确率更高哦！");
                        factorialRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10);
                    }
                    factorialNextTitleBtn.setDisable(false);
                } catch (Exception e) {
                    factorialSubmitAnsBtn.setDisable(false);
                    factorialAnsText.setDisable(false);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "输入内容不合法(内容不能为空、不能有字母等)！");
                    alert.setTitle("不合法输入！");
                    alert.setHeaderText("");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "请先输入题目数并确定！");
                alert.setTitle("不合法操作！");
                alert.setHeaderText("");
                alert.show();
            }
        });
        factorialNextTitleBtn.setOnMouseClicked(event -> {
            if (!factorialSubmitAnsBtn.isDisabled()) {
                if (i < titleNum) {
                    Alert noAnsTitle = new Alert(Alert.AlertType.CONFIRMATION, "你确定跳过这一题吗？");
                    noAnsTitle.setTitle("跳过这一题");
                    noAnsTitle.setHeaderText("");
                    Optional<ButtonType> result = noAnsTitle.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        //跳过这一题
                        factorialAnsText.setText("");
                        factorialRandTitle();
                        i++;
                        factorialTitleText.setText(titleString);
                    }
                } else {
                    ansAll();
                    factorialRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10 + "\t耗时：" + costTime + "s");
                    startDoTestTime = 0;
                    correctAnswerNum = 0;
                    correctRate = 0;
                    endDoTestTime = 0;
                    costTime = 0;
                    factorialNextTitleBtn.setDisable(true);
                }
            } else {
                if (i < titleNum) {
                    factorialRandTitle();
                    i++;
                    factorialTitleText.setText(titleString);
                    factorialAnsText.setText("");
                    factorialAnsText.setDisable(false);
                    factorialSubmitAnsBtn.setDisable(false);
                    factorialUserAnsText.setText("");
                    factorialRightAnsText.setText("");
                    factorialjudgeTF.setText("");
                } else {
                    ansAll();
                    factorialNextTitleBtn.setDisable(true);
                    factorialRightRate.setText("正确率：" + correctRate + "%\t" + "得分:" + correctAnswerNum * 10 + "\t耗时：" + costTime + "s");
                    startDoTestTime = 0;
                    correctAnswerNum = 0;
                    correctRate = 0;
                    endDoTestTime = 0;
                    costTime = 0;
                }
            }
        });

        factorialVBox.getChildren().addAll(factorialRequireHBox, factorialNumHBox, factorialTitleHBox, factorialAnsHBox, factorialJudgeVBox);

        ////////////////////////修改颜色
        Tab setScreenColor = new Tab("设置背景颜色");
        tabPane.getTabs().addAll(fourOperate, properFraction, factorialOperate, setScreenColor);
        Button whiteBut = new Button("设置为白色");
        Button purpleBut = new Button("设置为紫色");
        Button blueBut = new Button("设置为蓝色");
        Button yellowBut = new Button("设置为黄色");
        Button redBut = new Button("设置为红色");
        Button orangeBut = new Button("设置为橙色");
        Button greenBut = new Button("设置为绿色");
        whiteBut.setCursor(Cursor.HAND);
        whiteBut.setOnMouseClicked(event -> {
            tabPane.setStyle("-fx-background-color: white");
        });
        purpleBut.setCursor(Cursor.HAND);
        purpleBut.setOnMouseClicked(event -> {
            tabPane.setStyle("-fx-background-color: #ff00ff");
        });
        blueBut.setCursor(Cursor.HAND);
        blueBut.setOnMouseClicked(event -> {
            tabPane.setStyle("-fx-background-color: #5555ff");
        });
        yellowBut.setCursor(Cursor.HAND);
        yellowBut.setOnMouseClicked(event -> {
            tabPane.setStyle("-fx-background-color: #ffff88");
        });
        redBut.setCursor(Cursor.HAND);
        redBut.setOnMouseClicked(event -> {
            tabPane.setStyle("-fx-background-color: #ff5555");
        });
        orangeBut.setCursor(Cursor.HAND);
        orangeBut.setOnMouseClicked(event -> {
            tabPane.setStyle("-fx-background-color: orange");
        });
        greenBut.setCursor(Cursor.HAND);
        greenBut.setOnMouseClicked(event -> {
            tabPane.setStyle("-fx-background-color: #55FF55");
        });
        VBox colorVBox = new VBox();
        colorVBox.getChildren().addAll(whiteBut, purpleBut, blueBut, yellowBut, redBut, orangeBut, greenBut);
        colorVBox.setSpacing(20);
        colorVBox.setAlignment(Pos.CENTER);
        setScreenColor.setContent(colorVBox);

        Scene scene = new Scene(tabPane, 750, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("四则运算");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //答完所有的题
    private static void ansAll() {
        //初始化数据
        i = 1;
        factorialResult = 0;
        titleNum = 0;
        titleString = null;

        endDoTestTime = System.currentTimeMillis();
        costTime = (endDoTestTime - startDoTestTime) / 1000;
        Alert gameOver = new Alert(Alert.AlertType.WARNING, "这是最后一题啦！");
        gameOver.setTitle("题已答完");
        gameOver.setHeaderText("");
        gameOver.show();
    }

    //随机生成四则混合运算式题目及结果
    private static void randomTitle() {
        int[] data = new int[4];
        int[] op = new int[3];
        int arithmLength = 0;
        calculateResult = 0;                                          //存算术式的运算结果

        arithmLength = (int) (Math.random() * 2 + 3);              //算术式长度(3到4个数字)
        String[] titleTemp = new String[arithmLength];

        //根据运算数长度随机生成题目并计算该题目的结果
        switch (arithmLength) {
            case 3:
                for (int j = 0; j < arithmLength; j++) {                              //随机生成3个运算数字
                    data[j] = (int) (Math.random() * 201 - 100);
                    if (data[j] == 0) {
                        j--;
                    }
                }
                for (int j = 0; j < arithmLength - 1; j++) {                              //随机生成2个运算符
                    op[j] = (int) (Math.random() * 4);
                }
                if (op[0] > 1) {
                    calculate(data[0], op[0], data[1]);
                    calculate(calculateResult, op[1], data[2]);
                } else {
                    if (op[1] > 1) {
                        calculate(data[1], op[1], data[2]);
                        calculate(data[0], op[0], calculateResult);
                    } else {
                        calculate(data[0], op[0], data[1]);
                        calculate(calculateResult, op[1], data[2]);
                    }
                }
                for (int j = 0; j < arithmLength; j++) {
                    if (data[j] < 0) {
                        titleTemp[j] = "(" + data[j] + ")";
                    } else {
                        titleTemp[j] = data[j] + "";
                    }
                }
                titleString = titleTemp[0] + operators[op[0]] + titleTemp[1] + operators[op[1]] + titleTemp[2] + " = ";
                break;
            case 4:
                for (int j = 0; j < arithmLength; j++) {                              //随机生成4个运算数字
                    data[j] = (int) (Math.random() * 201 - 100);
                    if (data[j] == 0) {
                        j--;
                    }
                }
                for (int j = 0; j < arithmLength - 1; j++) {                              //随机生成3个运算符
                    op[j] = (int) (Math.random() * 4);
                }
                if (op[0] > 1) {
                    if (op[1] > 1) {
                        calculate(data[0], op[0], data[1]);
                        calculate(calculateResult, op[1], data[2]);
                        calculate(calculateResult, op[2], data[3]);
                    } else {
                        if (op[2] > 1) {
                            calculate(data[0], op[0], data[1]);
                            int temp = calculateResult;
                            calculate(data[2], op[2], data[3]);
                            calculate(temp, op[1], calculateResult);
                        } else {
                            calculate(data[0], op[0], data[1]);
                            calculate(calculateResult, op[1], data[2]);
                            calculate(calculateResult, op[2], data[3]);
                        }
                    }
                } else {
                    if (op[1] > 1) {
                        if (op[2] > 1) {
                            calculate(data[1], op[1], data[2]);
                            calculate(calculateResult, op[2], data[3]);
                            calculate(data[0], op[0], calculateResult);
                        } else {
                            calculate(data[1], op[1], data[2]);
                            calculate(data[0], op[0], calculateResult);
                            calculate(calculateResult, op[2], data[3]);
                        }
                    } else {
                        calculate(data[0], op[0], data[1]);
                        calculate(calculateResult, op[1], data[2]);
                        calculate(calculateResult, op[2], data[3]);
                    }
                }
                for (int j = 0; j < arithmLength; j++) {
                    if (data[j] < 0) {
                        titleTemp[j] = "(" + data[j] + ")";
                    } else {
                        titleTemp[j] = data[j] + "";
                    }
                }
                titleString = titleTemp[0] + operators[op[0]] + titleTemp[1] + operators[op[1]] + titleTemp[2] + operators[op[2]] + titleTemp[3] + " = ";
                break;
        }
    }

    private static void calculate(int data1, int op, int data2) {
        switch (op) {
            case 0://如果是加
                calculateResult = data1 + data2;
                break;
            case 1://如果是减
                calculateResult = data1 - data2;
                break;
            case 2://如果是乘
                calculateResult = data1 * data2;
                break;
            case 3://如果是除
                calculateResult = data1 / data2;
                break;
        }
    }

    //随机生成真分数题目
    private static void randomProperFraction() {
        int k;                                                         //循环变量
        int rand1 = 0, rand2 = 0;                                               //随机生成运算符下标
        int properFractionTitleLength;                                     //题目长度

        for (k = 0; k < 2; k++) {
            properFractionResult[k] = 0;
        }
        properFractionTitleLength = (int) (Math.random() * 2 + 2);
        int[] numerator = new int[properFractionTitleLength];     //分子
        int[] denominator = new int[properFractionTitleLength];   //分母
        switch (properFractionTitleLength) {
            case 2:
                for (k = 0; k < 2; k++) {
                    denominator[k] = (int) (Math.random() * 100 + 1);
                    numerator[k] = (int) (Math.random() * denominator[k] + 1);
                }
                rand1 = (int) (Math.random() * 4);
                calculateProperFraction(numerator[0], denominator[0], rand1, numerator[1], denominator[1]);
                reductionFraction(properFractionResult[0], properFractionResult[1]);
                titleString = numerator[0] + "/" + denominator[0] + "  " + operators[rand1] + "  " + numerator[1] + "/" + denominator[1] + " = ";
                break;
            case 3:
                for (k = 0; k < 3; k++) {
                    denominator[k] = (int) (Math.random() * 100 + 1);
                    numerator[k] = (int) (Math.random() * denominator[k] + 1);
                }
                rand1 = (int) (Math.random() * 4);
                rand2 = (int) (Math.random() * 4);
                if (rand1 == 2 || rand1 == 3) {                      //如果第一个运算符是乘或者除就先算
                    calculateProperFraction(numerator[0], denominator[0], rand1, numerator[1], denominator[1]);
                    calculateProperFraction(properFractionResult[0], properFractionResult[1], rand2, numerator[2], denominator[2]);
                } else {
                    calculateProperFraction(numerator[1], denominator[1], rand2, numerator[2], denominator[2]);
                    calculateProperFraction(numerator[0], denominator[0], rand1, properFractionResult[0], properFractionResult[1]);
                }
                reductionFraction(properFractionResult[0], properFractionResult[1]);
                titleString = numerator[0] + "/" + denominator[0] + "  " + operators[rand1] + "  " + numerator[1] + "/" + denominator[1] + "  " + operators[rand2] + "  " + numerator[2] + "/" + denominator[2] + " = ";
                break;
        }
    }

    //计算两个分数的运算结果
    private static void calculateProperFraction(int numerator1, int denominator1, int rand, int numerator2, int denominator2) {
        switch (rand) {
            case 0://如果是加号
                properFractionResult[1] = denominator1 * denominator2;
                properFractionResult[0] = numerator1 * denominator2 + numerator2 * denominator1;
                break;
            case 1://如果是减号
                properFractionResult[1] = denominator1 * denominator2;
                properFractionResult[0] = numerator1 * denominator2 - numerator2 * denominator1;
                break;
            case 2://如果是乘号
                properFractionResult[1] = denominator1 * denominator2;
                properFractionResult[0] = numerator1 * numerator2;
                break;
            case 3://如果是除号
                properFractionResult[1] = denominator1 * numerator2;
                properFractionResult[0] = numerator1 * denominator2;
                break;
        }
    }

    //将分数约分约到最简
    private static void reductionFraction(int numerator, int denominator) {
        int i = 0;
        int reductionFractionNumerator = 0, reductionFractionDenominator = 0;    //约分后的的分子、分母
        int commonDivisor = 1;
        for (i = numerator; i >= 1; i--) {
            if (numerator % i == 0 && denominator % i == 0) {
                commonDivisor = i;
                break;
            }
        }
        reductionFractionNumerator = numerator / commonDivisor;
        reductionFractionDenominator = denominator / commonDivisor;
        properFractionResult[0] = reductionFractionNumerator;                //约分后的的分子赋给properFractionResult[0]
        properFractionResult[1] = reductionFractionDenominator;              //约分后的的分母赋给properFractionResult[1]
    }

    //阶乘运算函数
    private static long factorial(long factorialData) {
        if (factorialData == 0) {
            return 1;
        } else {
            long factorialReturnResult = factorialData * factorial(factorialData - 1);
            return factorialReturnResult;
        }
    }

    //随机产生阶乘运算题目
    private static void factorialRandTitle() {
        long factorialData = (long) (Math.random() * 21);
        factorialResult = factorial(factorialData);
        titleString = factorialData + "! =";
    }
}