package TFCBestFormula;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class ListenerCreator {   //自定义监听类
    public static TargetSliderListener createTargetSliderListener(JTextField target){   //目标滑块监听器创建方法
        TargetSliderListener targetSliderListener = new TargetSliderListener();
        targetSliderListener.setTarget(target);
        return targetSliderListener;
    }

    public static TargetListener createTargetListener(JTextField target, ImgJSlider targetSlider){  //目标输入框监听器创建方法
        TargetListener targetListener = new TargetListener();
        targetListener.setJTextField(target);
        targetListener.setForgingSlider(targetSlider);
        return targetListener;
    }

    public static StepButtonListener createStepButtonListener(int i, ImgJSlider forgingSlider){ //步骤按钮监听器创建方法
        StepButtonListener stepButtonListener = new StepButtonListener();
        stepButtonListener.setButtonValue(Calculation.value[i]);
        stepButtonListener.setForgingSlider(forgingSlider);
        return stepButtonListener;
    }

    public static ChooseButtonListener createChooseButtonListener(){    //选择按钮监听器创建方法
        return new ChooseButtonListener();
    }

    public static ItemButtonListener createItemButtonListener(){    //工具按钮监听器创建方法
        return new ItemButtonListener();
    }

    public static StartButtonListener createStartButtonListener(ImgJSlider targetSlider, JTextArea log){    //开始按钮监听器创建方法
        StartButtonListener startButtonListener = new StartButtonListener();
        startButtonListener.setImgJSlider(targetSlider);
        startButtonListener.setLog(log);
        return startButtonListener;
    }
}

class TargetSliderListener implements ChangeListener {   //目标滑块监听类
    JTextField target;
    public void stateChanged(ChangeEvent e){
        ImgJSlider imgJSlider = (ImgJSlider)e.getSource();
        String value = imgJSlider.getValue() + "";
        target.setText(value);
    }

    public void setTarget(JTextField target){
        this.target = target;
    }
}

class TargetListener implements KeyListener {    //目标输入框监听类
    JTextField target;
    ImgJSlider targetSlider;
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        int textNum = 0;
        StringBuilder outText = new StringBuilder();
        String text = target.getText();
        if(key != 8 && key != 37 && key != 39){
            if(!text.isEmpty()){
                String[] num = text.split("\\D+");
                if(num[0].isEmpty()){
                    num = Arrays.copyOfRange(num, 1, num.length);
                }
                for(String s:num){
                    outText.append(s);
                }
                textNum = Integer.parseInt(outText.toString());
                if(textNum < 0){
                    textNum = 0;
                }
                else if(textNum > 145){
                    textNum = 145;
                }
            }
            target.setText(textNum + "");
            targetSlider.setValue(textNum);
        }
    }

    public void keyReleased(KeyEvent e){
        keyPressed(e);
    }

    public void keyTyped(KeyEvent e){
        keyPressed(e);
    }

    public void setJTextField(JTextField target){
        this.target = target;
    }

    public void setForgingSlider(ImgJSlider targetSlider){
        this.targetSlider = targetSlider;
    }
}

class StepButtonListener implements ActionListener { //步骤按钮监听类
    int buttonValue;
    ImgJSlider forgingSlider;
    public void actionPerformed(ActionEvent e){
        int value = forgingSlider.getValue() + buttonValue;
        if(value < 0){
            value = 0;
        }
        else if(value > 145){
            value = 145;
        }
        forgingSlider.setValue(value);
    }

    public void setButtonValue(int buttonValue){
        this.buttonValue = buttonValue;
    }

    public void setForgingSlider(ImgJSlider forgingSlider){
        this.forgingSlider = forgingSlider;
    }
}

class ChooseButtonListener implements ActionListener {   //选择按钮监听类
    public static MainUI mainUI;
    public static void setMainUI(MainUI mainUI){
        ChooseButtonListener.mainUI = mainUI;
    }
    public void actionPerformed(ActionEvent e){
        mainUI.remove(mainUI.anvilView);
        mainUI.add(mainUI.chooseView);
        mainUI.validate();
        mainUI.repaint();
    }
}

class ItemButtonListener implements ActionListener { //工具按钮监听类
    public static MainUI mainUI;
    public static String[] order = Arrays.copyOfRange(ConfigLoad.langText, 22, 27);
    public static void setMainUI(MainUI mainUI){
        ItemButtonListener.mainUI = mainUI;
    }
    public void actionPerformed(ActionEvent e){
        JButton itemButton = (JButton)e.getSource();
        int num = Integer.parseInt(itemButton.getName());
        AnvilView.runNum = num;
        mainUI.anvilView.mainPanel.remove(mainUI.anvilView.choose);
        mainUI.anvilView.choose = ImgCreator.createChooseJButton(42, 82, num + "");
        mainUI.anvilView.mainPanel.add(mainUI.anvilView.choose);
        mainUI.anvilView.mainPanel.remove(mainUI.anvilView.needMetal);
        if(num < ConfigLoad.internalNum){
            mainUI.anvilView.needMetal = ImgCreator.createImgJLabel(62, 136, "image/need_metal/" + ConfigLoad.needMetalText[Integer.parseInt(ConfigLoad.forgingText[num][8])][0] + ".png");
            mainUI.anvilView.needMetal.setToolTipText(ConfigLoad.langText[11] + ConfigLoad.needMetalText[Integer.parseInt(ConfigLoad.forgingText[num][8])][1]);
        }
        else {
            int i, j = 1;
            for(i = 0; i < ConfigLoad.internalMetalNum; i++){
                if(ConfigLoad.forgingText[num][8].equals(ConfigLoad.needMetalText[i][0])){
                    j = 0;
                    break;
                }
            }
            if(j == 0){
                mainUI.anvilView.needMetal = ImgCreator.createImgJLabel(62, 136, "image/need_metal/" + ConfigLoad.forgingText[num][8] + ".png");
                mainUI.anvilView.needMetal.setToolTipText(ConfigLoad.langText[11] + ConfigLoad.needMetalText[i][1]);
            }
            else{
                for(i = 0; i < ConfigLoad.externalMetalNum; i++){
                    if(ConfigLoad.forgingText[num][8].equals(ConfigLoad.needMetalText[ConfigLoad.internalMetalNum + i][0])){
                        break;
                    }
                }
                mainUI.anvilView.needMetal = ImgCreator.createImgJLabel(62, 136, num);
                mainUI.anvilView.needMetal.setToolTipText(ConfigLoad.langText[11] + ConfigLoad.needMetalText[ConfigLoad.internalMetalNum + i][1]);
            }
        }
        mainUI.anvilView.mainPanel.add(mainUI.anvilView.needMetal);
        for(int i = 0; i < 3; i++){
            mainUI.anvilView.mainPanel.remove(mainUI.anvilView.showLabel[i]);
            mainUI.anvilView.showLabel[i] = ImgCreator.createImgShowJLabel(194 - (38 * i), 14, Integer.parseInt(ConfigLoad.forgingText[num][i + 5]), Integer.parseInt(ConfigLoad.forgingText[num][i + 2]));
            if(!ConfigLoad.forgingText[num][i + 2].equals("0")){
                mainUI.anvilView.showLabel[i].setToolTipText(AnvilView.forgingName[Integer.parseInt(ConfigLoad.forgingText[num][i + 2]) - 1] + " " + order[Integer.parseInt(ConfigLoad.forgingText[num][i + 5]) - 1]);
            }
            mainUI.anvilView.mainPanel.add(mainUI.anvilView.showLabel[i]);
        }
        mainUI.anvilView.mainPanel.remove(mainUI.anvilView.hammer);
        mainUI.anvilView.hammer = ImgCreator.createImgJLabel(258, 136, "image/hammer.png");
        mainUI.anvilView.hammer.setToolTipText(ConfigLoad.langText[12]);
        mainUI.anvilView.mainPanel.add(mainUI.anvilView.hammer);
        mainUI.remove(mainUI.chooseView);
        mainUI.add(mainUI.anvilView);
        mainUI.validate();
        mainUI.repaint();
    }
}

class StartButtonListener implements ActionListener {   //开始按钮监听类
    ImgJSlider targetSlider;
    JTextArea log;
    public void actionPerformed(ActionEvent e){
        Log.outputLog(targetSlider.getValue(), log);
    }

    public void setImgJSlider(ImgJSlider targetSlider){
        this.targetSlider = targetSlider;
    }

    public void setLog(JTextArea log){
        this.log = log;
    }
}