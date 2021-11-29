# LiveDataBus
增強[UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData)，將其包裝成觀察者模式的事件總線

[![](https://jitpack.io/v/JaredDoge/LiveDataBus.svg)](https://jitpack.io/#JaredDoge/LiveDataBus)
# Getting started
1. Add it in your root build.gradle at the end of repositories:
   ```gradle
    allprojects {
      repositories {
        // ...
        maven { url 'https://jitpack.io' }
      }
    }
   ```
2. Add the dependency
   ```gradle
   dependencies {
	        implementation 'com.github.JaredDoge:LiveDataBus:{latest_version}'
	 }
   ```  
# Usage
#### observe
```kotlin
LiveDataBus.get<String>(key).observe(lifecycleOwner){
            print(it)
}
```
>當你的lifecycleOwner使用fragment時，可以調用擴充方法toViewLife()將其轉為[viewLifecycleOwner](https://developer.android.com/reference/androidx/fragment/app/Fragment#getViewLifecycleOwner()) 

#### setValue
```kotlin
LiveDataBus.get<String>(key).setValue(msg)
```
# SingleLiveData
當然，你可以單獨使用SingleLiveData，以確保訊息來源是可靠的。

例如在viewModel中:
```kotlin
class MainViewModel : ViewModel() {

    val showToast: SingleLiveData<String> = SingleLiveData<String>()
    
    fun show(){
        showToast.setValue("testMsg")
    }
}
```
Activity
```kotlin
mainViewModel.showToast.observe(this){
    //確保訊息來源是來自MainViewModel的showToast
    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
}  
```
# Dynamic Proxy
除了使用.get(key)取得總線外，還可以使用.of()的方式，避免key打錯的問題。
首先先建立一個interface
#### interface
```kotlin
interface SampleEvent {

    //Int為總線的資料型態      
    fun message(): SingleLiveData<Int>
    
    //可以建立多個總線
    //fun message2(): SingleLiveData<String>
    //fun message3(): StringLvieData<MyJavaBean>
    //....
}
```
>因為使用動態代理的關係，所以必須是interface
#### observe
```kotlin
LiveDataBus.of<SampleEvent>().message().observe(this){
  //it is Int
}
```
#### setValue
```kotlin
LiveDataBus.of<SampleEvent>().message().setValue(123)
```




       



    
