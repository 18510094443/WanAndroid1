package com.example.lenovo.wanandroid.ui.Main.acrivity;

import android.animation.Animator;
import android.content.Intent;

import com.airbnb.lottie.LottieAnimationView;
import com.example.lenovo.wanandroid.MainActivity;
import com.example.lenovo.wanandroid.R;
import com.example.lenovo.wanandroid.app.MyApp;
import com.example.lenovo.wanandroid.base.BaseActivity;
import com.example.lenovo.wanandroid.base.interfaces.IBasePresenter;
import com.example.lenovo.wanandroid.utils.StatusBarUtil;

import butterknife.BindView;

public class StartActivity extends BaseActivity {

    @BindView(R.id.one_animation)
    LottieAnimationView mOneAnimation;
    @BindView(R.id.two_animation)
    LottieAnimationView mTwoAnimation;
    @BindView(R.id.three_animation)
    LottieAnimationView mThreeAnimation;
    @BindView(R.id.four_animation)
    LottieAnimationView mFourAnimation;
    @BindView(R.id.five_animation)
    LottieAnimationView mFiveAnimation;
    @BindView(R.id.six_animation)
    LottieAnimationView mSixAnimation;
    @BindView(R.id.seven_animation)
    LottieAnimationView mSevenAnimation;
    @BindView(R.id.eight_animation)
    LottieAnimationView mEightAnimation;
    @BindView(R.id.nine_animation)
    LottieAnimationView mNineAnimation;
    @BindView(R.id.ten_animation)
    LottieAnimationView mTenAnimation;

    @Override
    protected void initData() {
        startAnimation(mOneAnimation, "W.json");
        startAnimation(mTwoAnimation, "A.json");
        startAnimation(mThreeAnimation, "N.json");
        startAnimation(mFourAnimation, "A.json");
        startAnimation(mFiveAnimation, "N.json");
        startAnimation(mSixAnimation, "D.json");
        startAnimation(mSevenAnimation, "R.json");
        startAnimation(mEightAnimation, "I.json");
        startAnimation(mNineAnimation, "O.json");
        startAnimation(mTenAnimation, "D.json");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected IBasePresenter createpresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void onDestroy() {
        cancelAnimation();
        super.onDestroy();
    }

    @Override
    protected void initToolbar() {
        if (!MyApp.isFirstRun) {
            jumpToMain();
            return;
        }
        MyApp.isFirstRun = false;
        StatusBarUtil.immersive(this);
    }

    private void jumpToMain() {

    }


    private void cancelAnimation() {
        cancelAnimation(mOneAnimation);
        cancelAnimation(mTwoAnimation);
        cancelAnimation(mThreeAnimation);
        cancelAnimation(mFourAnimation);
        cancelAnimation(mFiveAnimation);
        cancelAnimation(mSixAnimation);
        cancelAnimation(mSevenAnimation);
        cancelAnimation(mEightAnimation);
        cancelAnimation(mNineAnimation);
        cancelAnimation(mTenAnimation);
    }

    private void startAnimation(LottieAnimationView mLottieAnimationView, String animationName) {
        mLottieAnimationView.setAnimation(animationName);
        mLottieAnimationView.playAnimation();
        mLottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    private void cancelAnimation(LottieAnimationView mLottieAnimationView) {
        if (mLottieAnimationView != null) {
            mLottieAnimationView.cancelAnimation();
        }
    }
}
