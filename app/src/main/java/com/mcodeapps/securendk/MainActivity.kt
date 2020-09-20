package com.mcodeapps.securendk

import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringAESKeyFromJNI(): String
    external fun stringAESVIFromJNI(): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    fun encryptKey(v: View){
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(stringAESKeyFromJNI().toByteArray(Charsets.UTF_8), "AES")
        val ivps = IvParameterSpec(stringAESVIFromJNI().toByteArray(Charsets.UTF_8))
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivps)
        val byteArray = et_text.text.toString().toByteArray(Charsets.UTF_8)
        val dstBuff = cipher.doFinal(byteArray)
        val output = Base64.encodeToString(dstBuff, Base64.DEFAULT)
        tv_output_encrypt.text =  output
    }

    fun decryptKey(v: View){
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(stringAESKeyFromJNI().toByteArray(Charsets.UTF_8), "AES")
        val ivps = IvParameterSpec(stringAESVIFromJNI().toByteArray(Charsets.UTF_8))
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivps)
        val encryptByte = Base64.decode(tv_output_encrypt.text.toString(), Base64.DEFAULT)
        val dstBuff = cipher.doFinal(encryptByte)
        val output = String(dstBuff, Charsets.UTF_8)
        tv_output_decrypt.text =  output
    }

}
