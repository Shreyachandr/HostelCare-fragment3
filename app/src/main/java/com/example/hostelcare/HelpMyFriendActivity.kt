package com.example.hostelcare

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_help_me.*
import kotlinx.android.synthetic.main.activity_help_my_friend.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class HelpMyFriendActivity : AppCompatActivity() {

    lateinit var Homesick: RadioButton
    lateinit var Lonely: RadioButton
    lateinit var Depressed: RadioButton
    lateinit var Ragged: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_my_friend)
    }
    fun hmsf(v: View)
    {


        var pno=editText4.text.toString()
        var msg=editText5.text.toString()

        Homesick = findViewById(R.id.radioButton10) as RadioButton;
        Lonely = findViewById(R.id.radioButton20) as RadioButton;
        Depressed = findViewById(R.id.radioButton30) as  RadioButton;
        Ragged = findViewById(R.id.radioButton40) as RadioButton;

        if (Homesick.isChecked)
            msg = "Feeling Homesick"
        else if (Lonely.isChecked)
            msg = "Feeling Lonely"
        else if (Depressed.isChecked)
            msg = "Feeling Depressed"
        else if (Ragged.isChecked)
            msg = "Being Ragged"

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val uid=sharedPreference.getString("pno","")
        sendIKON(v);
        doAsync {
            val result = URL("http://nitkhostels.org/HostelCare/helpmf.php?uid="+uid+"&pno="+pno+"&msg="+msg).readText()
            uiThread {
                try {
                    toast(result)
                    val smsManager = SmsManager.getDefault() as SmsManager
                    smsManager.sendTextMessage("7892990151", null, "Help My Friend, Phone-No:" +pno+",Message="+msg,null, null)



                } catch (e: Exception) {

                    toast(e.toString());
                }
            }
        }
    }

    fun sendIKON(v: View) {

        var message=editText5.text.toString()

        Homesick = findViewById(R.id.radioButton10) as RadioButton;
        Lonely = findViewById(R.id.radioButton20) as RadioButton;
        Depressed = findViewById(R.id.radioButton30) as  RadioButton;
        Ragged = findViewById(R.id.radioButton40) as RadioButton;

        if (Homesick.isChecked)
            message = "Feeling Homesick"
        else if (Lonely.isChecked)
            message = "Feeling Lonely"
        else if (Depressed.isChecked)
            message = "Feeling Depressed"
        else if (Ragged.isChecked)
            message = "Being Ragged"

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val uid=sharedPreference.getString("pno","")
        var msg="Help My Friend, Phone-No="+uid+". Message= "+message
        val mobiles = "9886539400"
        val senderId = "NITKHS"



        //Send SMS API
        var mainUrl = "https://aikonsms.co.in/control/smsapi.php?"

        var reqParam = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode("nitkhostels", "UTF-8")
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("hostelcare2612", "UTF-8")
        reqParam += "&" + URLEncoder.encode("sender_id", "UTF-8") + "=" + URLEncoder.encode(senderId, "UTF-8")
        reqParam += "&" + URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode("TRANS", "UTF-8")
        reqParam += "&" + URLEncoder.encode("mobile_no", "UTF-8") + "=" + URLEncoder.encode(mobiles, "UTF-8")
        reqParam += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8")
        reqParam += "&" + URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode("send_sms", "UTF-8")


        val mURL = URL(mainUrl + reqParam)
        Log.d("AIKON", mainUrl + reqParam);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        try{
            with(mURL.openConnection() as HttpURLConnection) {
                // optional default is GET
                requestMethod = "POST"
                println("URL : $url")
                println("Response Code : $responseCode")
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    println("Response : $response")
                }

            }
            toast("Successful Sending of Message!");
            Log.d("AIKON", "Successfull");

        }catch (e: Exception) {
            // some other Exception occurred
            e.printStackTrace()
        }





    }
}

