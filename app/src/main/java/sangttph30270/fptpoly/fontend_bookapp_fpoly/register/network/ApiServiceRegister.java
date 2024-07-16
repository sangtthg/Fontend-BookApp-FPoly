package sangttph30270.fptpoly.fontend_bookapp_fpoly.register.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sangttph30270.fptpoly.fontend_bookapp_fpoly.register.model.OTPModel;

public interface ApiServiceRegister {
    @POST("api/send_otp")
    Call<ResponseBody> sendOtp(@Body OTPModel otpRequest);
}
