plugins {
    id("com.android.application")
}

android {
    namespace = "sangttph30270.fptpoly.fontend_bookapp_fpoly"
    compileSdk = 34

    defaultConfig {
        applicationId = "sangttph30270.fptpoly.fontend_bookapp_fpoly"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation ("io.socket:socket.io-client:2.0.0")
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-analytics")

    //----->V import
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")
    implementation("de.hdodenhof:circleimageview:3.1.0") //thư viện làm tròn ảnh
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.intuit.ssp:ssp-android:1.1.1")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.0")
    implementation("io.github.muddz:styleabletoast:2.4.0")
    implementation("com.airbnb.android:lottie:6.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    //<-----V import

}