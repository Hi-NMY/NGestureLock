# NGestureLock -- 自定义手势解锁

## 简介

- ### 一个简单的自定义手势锁View

## 使用 

- maven

   ```groovy
    allprojects {
        repositories {
            .....
            maven { url 'https://jitpack.io' }
        }
    }
   ```
- android studio

   ```groovy
    implementation 'com.github.nmy520:NGestureLock:0.1.0'
   ```

## Api用法
- ### xml和java中的方法二选一即可
- xml

    ```xml
     <com.example.gesturelock.GestureUnlock
          android:id="@+id/myunlock"
          app:circleDefaultColor=""    //圆初始状态颜色（默认为BLACK）
          app:circleErrorColor=""    //圆错误状态颜色（默认为RED）
          app:circleSelectColor=""    //圆选择状态颜色（默认为BLUE）
          app:circleSuccessColor=""    //圆成功状态颜色（默认为GREEN）
          app:lineErrorColor=""    //线错误状态颜色（默认为RED）
          app:lineSelectColor=""    //线选择状态颜色（默认为BLUE）
          app:lineSuccessColor=""    //线成功状态颜色（默认为GREEN）
          app:minSelect=""    //最小选择点数（当isSetUp为true时有效）
          app:isSetUp=""    //是否为设置密码模式（默认为false）
          app:determineTime=""    //等待时间（float类型，默认为0.5秒）
          app:lookLocus=""    //是否显示轨迹（默认为true）
          app:selectView=""    //被选择时样式（此功能未实现）
          app:errorView=""    //错误时样式（此功能未实现）
          app:defaultView=""    //默认样式（此功能未实现）
          app:successView=""    //成功时样式（此功能未实现）
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"/>
    ```
- java（功能介绍与xml一样）

    ```java
     GestureUnlock myunlock = this.findViewById(R.id.myunlock);
        myunlock.setCircleDefaultColor();
        myunlock.setCircleErrorColor();
        myunlock.setCircleSelectColor();
        myunlock.setCircleSuccessColor();
        myunlock.setLineErrorColor();
        myunlock.setLineSelectColor();
        myunlock.setLineSuccessColor();
        myunlock.setMinSelect();
        myunlock.setDetermineTime();
        myunlock.setLookLocus();
        muunlock.setSetUp();
        myunlock.setDefaultKey();    //写入密码（正确的密码）
        
        与其对应get方法不再赘述
    ```
- 监听接口

    ```java
         myunlock.setIGestureListener(new IGestureListener() {
                @Override
                public void isSuccessful(String key) {
                    //验证成功回调  key为正确密码
                }
    
                @Override
                public void isError(String key) {
                    //验证错误回调  key为错误密码
                }
    
                @Override
                public void isSetUp(String key) {
                    //设置密码成功回调  key为设置密码（当isSetUp为true时回调）
                }
            }); 
    ```
## 联系我 ##
- QQ 944273286
