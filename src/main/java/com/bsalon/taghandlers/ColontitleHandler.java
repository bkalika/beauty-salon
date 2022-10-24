package com.bsalon.taghandlers;

import com.bsalon.controllers.commands.RegistrationCommand;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author @bkalika
 */
public class ColontitleHandler extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("</div>\n" +
                    "</div>\n" +
                    "<!-- partial -->\n");
            pageContext.getOut().write("</body>\n" +
                    "</html>");
        } catch (IOException e) {
            LOGGER.error(e);
            throw new JspException();
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() {
        return SKIP_BODY;
    }
}
