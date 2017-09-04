package jp.mikuappend.dialog;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hecateball
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class DialogRequest implements Serializable {

    @XmlElement(name = "utt")
    private String utt;
    @XmlElement(name = "context")
    private String context;
    @XmlElement(name = "nickname")
    private String nickname;
    @XmlElement(name = "nickname_y")
    private String nicknameY;
    @XmlElement(name = "sex")
    private String sex;
    @XmlElement(name = "bloodtype")
    private String bloodType;
    @XmlElement(name = "birthdateY")
    private String birthdateY;
    @XmlElement(name = "birthdateM")
    private String birthdateM;
    @XmlElement(name = "birthdateD")
    private String birthdateD;
    @XmlElement(name = "age")
    private String age;
    @XmlElement(name = "constellations")
    private String constellations;
    @XmlElement(name = "place")
    private String place;
    @XmlElement(name = "mode")
    private String mode;

    public String getUtt() {
        return utt;
    }

    public void setUtt(String utt) {
        this.utt = utt;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNicknameY() {
        return nicknameY;
    }

    public void setNicknameY(String nicknameY) {
        this.nicknameY = nicknameY;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBirthdateY() {
        return birthdateY;
    }

    public void setBirthdateY(String birthdateY) {
        this.birthdateY = birthdateY;
    }

    public String getBirthdateM() {
        return birthdateM;
    }

    public void setBirthdateM(String birthdateM) {
        this.birthdateM = birthdateM;
    }

    public String getBirthdateD() {
        return birthdateD;
    }

    public void setBirthdateD(String birthdateD) {
        this.birthdateD = birthdateD;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getConstellations() {
        return constellations;
    }

    public void setConstellations(String constellations) {
        this.constellations = constellations;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
