package com.example.hostelcare

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.net.URLEncoder

class ff {
    internal fun zx() {
        /*Send SMS using JAVA*/

        //Your authentication key
        val username = "UserName"
        //Your authentication key
        val password = "Password"
        //Multiple mobiles numbers separated by comma
        val mobiles = "9999999"
        //Sender ID,While using route4 sender id should be 6 characters long.
        val senderId = "ABCDEF"
        //Your message to send, Add URL encoding here.
        val message = "Test message"
        //define service
        val service = "service"
        //define method
        val method = "send_sms"

        //Prepare Url
        var myURLConnection: URLConnection? = null
        var myURL: URL? = null
        var reader: BufferedReader? = null

        //encoding message
        val encoded_message = URLEncoder.encode(message)

        //Send SMS API
        var mainUrl = "https://aikonsms.co.in/control/smsapi.php?"

        //Prepare parameter string
        val sbPostData = StringBuilder(mainUrl)
        sbPostData.append("user_name=$username")
        sbPostData.append("&password=$password")
        sbPostData.append("&mobiles=$mobiles")
        sbPostData.append("&message=$encoded_message")
        sbPostData.append("&service=$service")
        sbPostData.append("&sender_id=$senderId")
        sbPostData.append("&method=$method")

        //final string
        mainUrl = sbPostData.toString()
        try {
            //prepare connection
            myURL = URL(mainUrl)
            myURLConnection = myURL.openConnection()
            myURLConnection!!.connect()
            reader = BufferedReader(InputStreamReader(myURLConnection.getInputStream()))
            //reading response
            var response: String

            //finally close connection
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
