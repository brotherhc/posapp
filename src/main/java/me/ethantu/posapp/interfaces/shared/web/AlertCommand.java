package me.ethantu.posapp.interfaces.shared.web;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 7/9/14
 * Time: 10:27 PM
 */
public class AlertCommand {

    public static final String ALERT_COMMAND_KEY = "alertCommand";

    private AlertType type;

    private String content;

    public AlertCommand() {
    }

    public AlertCommand(AlertType type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public enum AlertType {
        SUCCESS("成功", 0),
        INFO("信息", 1),
        WARNING("警告", 2),
        DANGER("错误", 3);

        private AlertType(String name, int value) {
            this.name = name;
            this.value = value;
        }

        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }
}
