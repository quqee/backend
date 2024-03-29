package org.hits.backend.hackaton.core.mail;

import org.hits.backend.hackaton.public_interface.defect.DefectFullDto;
import org.hits.backend.hackaton.public_interface.statement.StatementFullDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailFormatter {
    public String formatFullStatement(StatementFullDto dto, List<DefectFullDto> defects) {
        var builder = new StringBuilder();

        builder.append("""
                        
                        <!DOCTYPE html>
                        <html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
                        <head>
                          <meta charset="utf-8">
                          <meta name="viewport" content="width=device-width">
                          <meta http-equiv="X-UA-Compatible" content="IE=edge">
                          <meta name="x-apple-disable-message-reformatting">
                          <title></title>
                          <style>
                            html,
                            body {
                              margin: 0 auto !important;
                              padding: 0 !important;
                              height: 100% !important;
                              width: 100% !important;
                              background: #f1f1f1;
                            }
                                                
                            * {
                              -ms-text-size-adjust: 100%;
                              -webkit-text-size-adjust: 100%;
                            }
                                                
                            div[style*="margin: 16px 0"] {
                              margin: 0 !important;
                            }
                                                
                            table,
                            td {
                              mso-table-lspace: 0pt !important;
                              mso-table-rspace: 0pt !important;
                            }
                                                
                            table {
                              border-spacing: 0 !important;
                              border-collapse: collapse !important;
                              table-layout: fixed !important;
                              margin: 0 auto !important;
                            }
                                                
                            img {
                              -ms-interpolation-mode:bicubic;
                            }
                                                
                            a {
                              text-decoration: none;
                            }
                                                
                            *[x-apple-data-detectors],
                            .unstyle-auto-detected-links *,
                            .aBn {
                              border-bottom: 0 !important;
                              cursor: default !important;
                              color: inherit !important;
                              text-decoration: none !important;
                              font-size: inherit !important;
                              font-family: inherit !important;
                              font-weight: inherit !important;
                              line-height: inherit !important;
                            }
                                                
                            .a6S {
                              display: none !important;
                              opacity: 0.01 !important;
                            }
                                                
                            .im {
                              color: inherit !important;
                            }
                                                
                            img.g-img + div {
                              display: none !important;
                            }
                                                
                            @media only screen and (min-device-width: 320px) and (max-device-width: 374px) {
                              u ~ div .email-container {
                                min-width: 320px !important;
                              }
                            }
                                                
                            @media only screen and (min-device-width: 375px) and (max-device-width: 413px) {
                              u ~ div .email-container {
                                min-width: 375px !important;
                              }
                            }
                                                
                            @media only screen and (min-device-width: 414px) {
                              u ~ div .email-container {
                                min-width: 414px !important;
                              }
                            }
                          </style>
                                                
                                                
                          <style>
                                                
                            .primary{
                              background: #448ef6;
                            }
                            .bg_white{
                              background: #ffffff;
                            }
                            .bg_light{
                              background: #fafafa;
                            }
                            .bg_black{
                              background: #000000;
                            }
                            .bg_dark{
                              background: rgba(0,0,0,.8);
                            }
                            .email-section{
                              padding:2.5em;
                            }
                                                
                            .btn{
                              padding: 5px 15px;
                              display: inline-block;
                            }
                            .btn.btn-primary{
                              border-radius: 30px;
                              background: #448ef6;
                              color: #ffffff;
                            }
                            .btn.btn-white{
                              border-radius: 30px;
                              background: #ffffff;
                              color: #000000;
                            }
                            .btn.btn-white-outline{
                              border-radius: 30px;
                              background: transparent;
                              border: 1px solid #fff;
                              color: #fff;
                            }
                                                
                            h1,h2,h3,h4,h5,h6{
                              font-family: 'Work Sans', sans-serif;
                              color: #000000;
                              margin-top: 0;
                              font-weight: 400;
                            }
                                                
                            body{
                              font-family: 'Work Sans', sans-serif;
                              font-weight: 400;
                              font-size: 15px;
                              line-height: 1.8;
                              color: rgba(0,0,0,.4);
                            }
                                                
                            a{
                              color: #448ef6;
                            }
                                                
                            table{
                            }
                                                
                            .logo h1{
                              margin: 0;
                            }
                            .logo h1 a{
                              color: #000000;
                              font-size: 20px;
                              font-weight: 700;
                              text-transform: uppercase;
                              font-family: 'Poppins', sans-serif;
                            }
                                                
                            .navigation{
                              padding: 0;
                            }
                            .navigation li{
                              list-style: none;
                              display: inline-block;;
                              margin-left: 5px;
                              font-size: 13px;
                              font-weight: 500;
                            }
                            .navigation li a{
                              color: rgba(0,0,0,.4);
                            }
                                                
                            .hero{
                              position: relative;
                              z-index: 0;
                            }
                                                
                            .hero .overlay{
                              position: absolute;
                              top: 0;
                              left: 0;
                              right: 0;
                              bottom: 0;
                              content: '';
                              width: 100%;
                              background: #000000;
                              z-index: -1;
                              opacity: .3;
                            }
                                                
                            .hero .text{
                              color: rgba(255,255,255,.9);
                            }
                            .hero .text h2{
                              color: #fff;
                              font-size: 50px;
                              margin-bottom: 0;
                              font-weight: 300;
                              line-height: 1;
                            }
                            .hero .text h2 span{
                              font-weight: 600;
                              color: #448ef6;
                            }
                                                
                            .heading-section{
                            }
                            .heading-section h2{
                              color: #000000;
                              font-size: 28px;
                              margin-top: 0;
                              line-height: 1.4;
                              font-weight: 400;
                            }
                            .heading-section .subheading{
                              margin-bottom: 20px !important;
                              display: inline-block;
                              font-size: 13px;
                              text-transform: uppercase;
                              letter-spacing: 2px;
                              color: rgba(0,0,0,.4);
                              position: relative;
                            }
                            .heading-section .subheading::after{
                              position: absolute;
                              left: 0;
                              right: 0;
                              bottom: -10px;
                              content: '';
                              width: 100%;
                              height: 2px;
                              background: #448ef6;
                              margin: 0 auto;
                            }
                                                
                            .heading-section-white{
                              color: rgba(255,255,255,.8);
                            }
                            .heading-section-white h2{
                              font-family:
                              line-height: 1;
                              padding-bottom: 0;
                            }
                            .heading-section-white h2{
                              color: #ffffff;
                            }
                            .heading-section-white .subheading{
                              margin-bottom: 0;
                              display: inline-block;
                              font-size: 13px;
                              text-transform: uppercase;
                              letter-spacing: 2px;
                              color: rgba(255,255,255,.4);
                            }
                                                
                            .text-services .meta{
                              text-transform: uppercase;
                              font-size: 14px;
                              margin-bottom: 0;
                            }
                                                
                            .footer{
                              color: rgba(255,255,255,.5);
                                                
                            }
                            .footer .heading{
                              color: #ffffff;
                              font-size: 20px;
                            }
                            .footer ul{
                              margin: 0;
                              padding: 0;
                            }
                            .footer ul li{
                              list-style: none;
                              margin-bottom: 10px;
                            }
                            .footer ul li a{
                              color: rgba(255,255,255,1);
                            }
                                                
                            .borderCenter {
                              text-align: center;
                              border-top: 1px solid black;
                              border-bottom: 1px solid black;
                              padding: 20px 0;
                            }
                                                
                            @media screen and (max-width: 500px) {
                            }
                                                
                          </style>
                                                
                          <meta name="robots" content="noindex, follow">
                          <script nonce="9b9f3dcd-4116-4f34-a2b6-8be59e0aa99f">try{(function(w,d){!function(k,l,m,n){k[m]=k[m]||{};k[m].executed=[];k.zaraz={deferred:[],listeners:[]};k.zaraz.q=[];k.zaraz._f=function(o){return async function(){var p=Array.prototype.slice.call(arguments);k.zaraz.q.push({m:o,a:p})}};for(const q of["track","set","debug"])k.zaraz[q]=k.zaraz._f(q);k.zaraz.init=()=>{var r=l.getElementsByTagName(n)[0],s=l.createElement(n),t=l.getElementsByTagName("title")[0];t&&(k[m].t=l.getElementsByTagName("title")[0].text);k[m].x=Math.random();k[m].w=k.screen.width;k[m].h=k.screen.height;k[m].j=k.innerHeight;k[m].e=k.innerWidth;k[m].l=k.location.href;k[m].r=l.referrer;k[m].k=k.screen.colorDepth;k[m].n=l.characterSet;k[m].o=(new Date).getTimezoneOffset();if(k.dataLayer)for(const x of Object.entries(Object.entries(dataLayer).reduce(((y,z)=>({...y[1],...z[1]})),{})))zaraz.set(x[0],x[1],{scope:"page"});k[m].q=[];for(;k.zaraz.q.length;){const A=k.zaraz.q.shift();k[m].q.push(A)}s.defer=!0;for(const B of[localStorage,sessionStorage])Object.keys(B||{}).filter((D=>D.startsWith("_zaraz_"))).forEach((C=>{try{k[m]["z_"+C.slice(7)]=JSON.parse(B.getItem(C))}catch{k[m]["z_"+C.slice(7)]=B.getItem(C)}}));s.referrerPolicy="origin";s.src="/cdn-cgi/zaraz/s.js?z="+btoa(encodeURIComponent(JSON.stringify(k[m])));r.parentNode.insertBefore(s,r)};["complete","interactive"].includes(l.readyState)?zaraz.init():k.addEventListener("DOMContentLoaded",zaraz.init)}(w,d,"zarazData","script");})(window,document)}catch(e){throw fetch("/cdn-cgi/zaraz/t"),e;};</script></head>
                        <body width="100%" style="margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #222222;">
                        <center style="width: 100%; background-color: #f1f1f1;">
                          <div style="display: none; font-size: 1px;max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;">
                            &zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;&zwnj;&nbsp;
                          </div>
                          <div style="max-width: 600px; margin: 0 auto;" class="email-container">
                                                
                            <table align="center" role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%" style="margin: auto;">
                              <tr>
                                <td valign="top" class="bg_white" style="padding: 1em 2.5em;">
                                  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                      <td class="logo" style="text-align: center; color: #000000;
                                                                                             font-size: 20px;
                                                                                             font-weight: 700;
                                                                                             text-transform: uppercase;
                                                                                             font-family: 'Poppins', sans-serif;">
                        """);
        builder.append("""
                <h1 style="color: #000000;
                                  font-size: 20px;
                                  font-weight: 700;
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;"><a href="#">Ведомость</a></h1>
                                    <ul class="navigation" style="padding: 0;list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  color: rgba(0,0,0,.4);
                                  font-weight: 500;">
                """);
        builder.append("""
                <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  font-size: 20px;
                                  color: rgba(0,0,0,.4);
                                  font-weight: 700;
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Название:\s
                """);
        builder.append(dto.areaName());
        builder.append("</a></li>");

        builder.append("""
                <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  font-size: 20px;
                                  font-weight: 700;
                                  color: rgba(0,0,0,.4);
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Длина:\s
                """);
        builder.append(dto.length() + "м");
        builder.append("</a></li>");
        builder.append("""
                <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  font-size: 20px;
                                  color: rgba(0,0,0,.4);
                                  font-weight: 700;
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Тип дороги: 
                """);
        builder.append(dto.roadType().name());
        builder.append("</a></li>");
        builder.append("""
                 <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  font-size: 20px;
                                  font-weight: 700;
                                  color: rgba(0,0,0,.4);
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Тип поверхности:
                """);
        builder.append(dto.surfaceType().name());
        builder.append("</a></li>");
        builder.append("""
                <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  font-size: 20px;
                                  font-weight: 700;
                                  color: rgba(0,0,0,.4);
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Направление:
                """);
        builder.append(dto.direction());
        builder.append("</a></li>");
        builder.append("""
                <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  font-size: 20px;
                                  font-weight: 700;
                                  color: rgba(0,0,0,.4);
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Срок сдачи:
                """);
        builder.append(dto.deadline());
        builder.append("</a></li>");
        builder.append("""
                <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  font-size: 20px;
                                  color: rgba(0,0,0,.4);
                                  font-weight: 700;
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Создано:\s
                """);
        builder.append(dto.createdAt());
        builder.append("</a></li>");
        builder.append("""
                <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  color: rgba(0,0,0,.4);
                                  font-size: 20px;
                                  font-weight: 700;
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Описание:\s
                """);
        builder.append(dto.description());
        builder.append("</a></li>");
        builder.append("""
                <li style="list-style: none;
                                  display: inline-block;;
                                  margin-left: 5px;
                                  font-size: 13px;
                                  font-weight: 500;
                                  color: rgba(0,0,0,.4);"><a href="#" style="color: #000000;
                                  color: rgba(0,0,0,.4);
                                  font-size: 20px;
                                  font-weight: 700;
                                  text-transform: uppercase;
                                  font-family: 'Poppins', sans-serif;">Статус:\s
                """);
        builder.append(dto.status().name());
        builder.append("</a></li>");

        builder.append("""
                </ul>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td valign="middle" class="hero bg_white" style="background-image: url(images/bg_1.jpg); background-size: cover; height: 200px;">
                          <div class="overlay"></div>
                          <table>
                            <tr>
                              <td>
                                <div class="text" style="padding: 0 4em; text-align: center;">
                                  <h1 class="heading-section-white">Дефекты</h1>
                                </div>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td class="bg_white email-section">
                          <div class="heading-section" style="text-align: center; padding: 0 30px;">
                            <p>Дорожные дефекты</p>
                          </div>
                          <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                            <div class="borderCenter" style="text-align: center;text-align: center;
                                                          border-top: 1px solid black;
                                                          border-bottom: 1px solid black;
                                                          padding: 20px 0;" >
                                                          <p style="color: #222222;"><strong>До начала работ</strong></p>
                """);

        for (DefectFullDto defect : defects) {
            for (int i = 0; i < defect.imagesBefore().size(); i++) {
                builder.append("<img style=\"max-width: 500px\" src=\"");
                builder.append(defect.imagesBefore().get(i));
                builder.append("\" alt style=\"width: 100%; max-width: 600px; height: auto; margin: auto; display: block;\">");
            }

            builder.append("<p class=\"meta\"><span>(");
            builder.append(defect.latitude());
            builder.append(", ");
            builder.append(defect.longitude());
            builder.append(")</span></p>");
            builder.append("<h3>");
            builder.append(defect.type().name());
            builder.append("</h3>");

            if (defect.defectDistance() != null) {
                builder.append("<p>");
                builder.append(defect.defectDistance());
                builder.append("м</p>");
            }

            for (int i = 0; i < defect.imagesAfter().size(); i++) {
                builder.append("<p style=\"color: #222222; ; margin: 70px 0;\"><strong>После начала работ</strong></p>");
                builder.append("<img style=\"max-width: 500px\" src=\"");
                builder.append(defect.imagesAfter().get(i));
                builder.append("\" alt style=\"width: 100%; max-width: 600px; height: auto; margin: auto; display: block;\">");
            }
        }

        builder.append("""
                </div>
                          </table>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                                
                    </table>
                    <table align="center" role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%" style="margin: auto;">
                      <tr>
                        <td valign="middle" class="bg_black footer email-section">
                          <table>
                            <tr>
                              <td valign="top" width="33.333%" style="padding-top: 20px;">
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                  <tr>
                                    <td style="text-align: left; padding-right: 10px;">
                                      <h3 class="heading">О нас</h3>
                                      <p>Мы делаем лучшее в сфере дорог.</p>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                              <td valign="top" width="33.333%" style="padding-top: 20px;">
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                  <tr>
                                    <td style="text-align: left; padding-left: 5px; padding-right: 5px;">
                                      <h3 class="heading">Контактная информация</h3>
                                      <ul>
                                        <li><span class="text">улица Ленина д. 3, город Пермь, Российская Федерация </span></li>
                                        <li><span class="text">+7 (962) 978-39-31</span></a></li>
                                      </ul>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                              <td valign="top" width="33.333%" style="padding-top: 20px;">
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                  <tr>
                                    <td style="text-align: left; padding-left: 10px;">
                                      <h3 class="heading">Полезные ссылки</h3>
                                      <ul>
                                        <li><a href="#">Компания</a></li>
                                        <li><a href="#">Сервисы</a></li>
                                        <li><a href="#">Работа</a></li>
                                        <li><a href="#">Полезная информация</a></li>
                                      </ul>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td valign="middle" class="bg_black footer email-section">
                          <table>
                            <tr>
                              <td valign="top" width="33.333%">
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                  <tr>
                                    <td style="text-align: left; padding-right: 10px;">
                                      <p>&copy; 2024. Все права защищены</p>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                              <td valign="top" width="33.333%">
                                <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                  <tr>
                                    <td style="text-align: right; padding-left: 5px; padding-right: 5px;">
                                      <p><a href="#" style="color: rgba(255,255,255,.4);">Отписаться</a></p>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </div>
                </center>
                </body>
                </html>
                """);

        return builder.toString();
    }
}
