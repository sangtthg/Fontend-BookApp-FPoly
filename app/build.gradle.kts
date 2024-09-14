plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "frontend_book_market_app.polytechnic.client"
    compileSdk = 34

    defaultConfig {
        applicationId = "frontend_book_market_app.polytechnic.client"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
    sourceSets {
        getByName("main") {
            res {
                srcDirs(
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\home",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\profile",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\book_details"
                )
            }
        }
    }
}

dependencies {
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1") // Cập nhật phiên bản nếu cần


    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime:2.6.1")
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.9.0")
    implementation("io.github.chaosleung:pinview:1.4.4")
    implementation("cn.bingoogolapple:bga-badgeview:1.1.3")

    //
    implementation("io.socket:socket.io-client:2.0.0")
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")
    implementation("de.hdodenhof:circleimageview:3.1.0") // thư viện làm tròn ảnh
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.0")
    implementation("io.github.muddz:styleabletoast:2.4.0")
    implementation("com.airbnb.android:lottie:6.0.0")
    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.intuit.ssp:ssp-android:1.1.1")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("com.github.travijuu:numberpicker:1.0.7")
    implementation("com.github.colourmoon:readmore-textview:v1.0.2")
    implementation("com.apachat:swipereveallayout-android:1.1.2")
    implementation("com.kongzue.dialogx:DialogX:0.0.49")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("org.aviran.cookiebar2:cookiebar2:1.1.5")
    implementation("dev.shreyaspatil.MaterialDialog:MaterialDialog:2.2.3")
    implementation("com.airbnb.android:lottie:3.3.6")
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.google.firebase:firebase-messaging:24.0.1")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


//    implementation("com.github.anton46:StepsView:1.0.3")
//    implementation 'com.github.anton46:StepsView:1.0.3'
    //    implementation("com.chaos.view:pinview:1.4.4")
    //    implementation("q.rorbin:badgeview:1.1.3")

}
