package nl.avans.smtpstatemachine;

import nl.avans.SmtpContext;

public class ReceivingDataState implements SmtpStateInf {
    SmtpContext context;
//    boolean crReceived;
//    boolean dotReceived;

    String data;

    public ReceivingDataState(SmtpContext context) {
        this.context = context;
    }

    @Override
    public void Handle(String data) {
        //handle the receiving of the mailbody
        // "\r\n.\r\n" should return to the WaitForMailFromState
        System.out.println(data);
        if(data.contains("<CR><LF>.<CR><LF>")) {
            context.SendData("250 Hello " + context.GetHost() + ", I am glad to meet you");
            context.SetNewState(new WaitForMailFromState(context));
        } else {
            this.data += data;
        }

        if(data.toUpperCase().startsWith("QUIT")) {
            context.SendData("221 Bye");
            context.DisconnectSocket();
            return;
        }
    }
}
