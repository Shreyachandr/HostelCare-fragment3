package com.example.hostelcare

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {


    companion object
    {
        var pno="";
        var name="";
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
       val log=sharedPreference.getString("pno","");
       if(log!="")
       {
           startActivity<NavActivity>();
        }
        else
       {
           setContentView(R.layout.activity_main)
       }
    }
   fun auth(v: View)
    {
       Log.d("SharedPref","Value Logged!");
        var name=name.text.toString()
        var pno=pno.text.toString()
        var dept=dept.text.toString()
        var bname=bname.text.toString()
        var bno=bno.text.toString()

        //For Dev Purposes

      doAsync {
            val result = URL("http://nitkhostels.org/HostelCare/studentreg.php?name="+name+"&pno="+pno+"&dept="+dept+"&bname="+bname+"&bno="+bno).readText()
          uiThread {
               try {
                   toast(result);
                   //Reregistering after Uninstallation.
                   if(result=="Already registered"){

                   }
                   //First time registrants
                   else{
                        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                       var editor = sharedPreference.edit()
                       editor.putString("pno",pno)
                        editor.commit()
                        MainActivity.pno= pno
                        MainActivity.name = name;
                        startActivity<NavActivity>();
                   }
               } catch (e: Exception) {
                   toast(e.toString());
                }
            }
        }
    }



}
