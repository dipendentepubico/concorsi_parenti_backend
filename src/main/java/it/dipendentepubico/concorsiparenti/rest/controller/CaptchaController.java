package it.dipendentepubico.concorsiparenti.rest.controller;

import com.pig4cloud.captcha.SpecCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;


@Tag(name = "Endpoint per ottenere il captcha" )
@RequestMapping("api/captcha")
@CrossOrigin(origins = "${concorsiparenti.frontend.cors.url}", allowCredentials = "true")
@Validated
@Controller
public class CaptchaController {

    public static final String ATTRIBUTE_CAPTCHA = "captcha";

    @GetMapping()
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set the request header to output image type
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // The three parameters are width, height and digits
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // Set the font
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32)); ;   // There is a default font, you can set the type without
        // Setting the, and alphanumeric mixture
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        // The verification code is stored in session
        request.getSession().setAttribute(ATTRIBUTE_CAPTCHA, specCaptcha.text().toLowerCase());

        // Output picture stream
        specCaptcha.out(response.getOutputStream());
    }


}