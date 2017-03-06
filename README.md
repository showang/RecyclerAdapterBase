![build](https://api.travis-ci.org/showang/RecyclerAdapterBase.svg?branch=master)
[![codecov](https://codecov.io/gh/showang/RecyclerAdapterBase/branch/master/graph/badge.svg)](https://codecov.io/gh/showang/RecyclerAdapterBase)
# RecyclerAdapterBase
Lazy base adapter to help you develop recycler view faster.

## Support header and footer view
<img src="https://cloud.githubusercontent.com/assets/780712/20781231/4c7575b2-b7bc-11e6-9d1f-d5abf294aad4.gif" width = "192" height = "341" alt="header_footer_view" align=center />
## Support load more

| load more and footer  | load more error handling  |
|---|---|
| <img src="https://cloud.githubusercontent.com/assets/780712/20784975/977a9e86-b7d8-11e6-8771-234ca0ff8bb0.gif" width = "192" height = "341" alt="loadmore_footer" align=center />  | <img src="https://cloud.githubusercontent.com/assets/780712/20781270/8c7a4ffc-b7bc-11e6-92bf-4b4448a69a63.gif" width = "192" height = "341" alt="load_more_error" align=center />  | 
|   |   |


## Download
```
compile 'tw.showang.android:recycleradapterbase:1.0.0'
```
If not found from jCenter, plz includ following script in project build.gradle.
```
allprojects {
    repositories {
        maven {
            url 'https://dl.bintray.com/showang/AndroidToolkits'
        }
    }
}
```

# Example
 Use [GitHub Api](https://developer.github.com/v3/) to show adapter function, you have to register an api key before run the example.

 Add GitHub api key into local.properties
> github.token= [your api key]

