#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_mcodeapps_securendk_MainActivity_stringAESKeyFromJNI(
        JNIEnv *env,
        jobject) {
    std::string aes_key = "2ED87AD94443AE3A";
    return env->NewStringUTF(aes_key.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mcodeapps_securendk_MainActivity_stringAESVIFromJNI(
        JNIEnv *env,
        jobject) {
    std::string aes_vi = "1F96C58DDE3E2A17";
    return env->NewStringUTF(aes_vi.c_str());
}