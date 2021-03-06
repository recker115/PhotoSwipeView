# PhotoSwipeView.  [![](https://jitpack.io/v/recker115/PhotoSwipeView.svg)](https://jitpack.io/#recker115/PhotoSwipeView)

Swipe list of photos right , left and up with animation like tinder.

![](photoSwipe.gif)

**Usage Instructions** :-

In root level `build.gradle` :-
```    
allprojects {
   repositories {
       ...
       maven { url 'https://jitpack.io' }
   }
}
```
 
 In app level `build.gradle` file use , i.e, in dependencies:-
```
     dependencies {
	 implementation 'com.github.recker115:PhotoSwipeView:v1.1'
     }
```	
     
 
First add the widget `photoView` to your xml file

     <com.recker.photoswipeview.PhotoSwipeView
        android:id="@+id/photoSwipeView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:animationDuration="100"
        app:photoLayout="@layout/photos_root_layout" />
        
For now, you can customize `two attributes` of the widget 

1 -> `animationDuration` for right and left swipe        
2 -> `photoLayout` for the layout to be swiped , i.e, `each photo`

Each photo object should be extended from `Photo` provided should have a link -- for photos :- 

     val photo = object : Photo() {
                override val url: String?
                    get() = "https://photosUrl"
      })
      
 Then add them to a list and set it to the `photoSwipe` view:-
 
      photoSwipeView.setPhotos(listOf(photo))


In the activity / fragment set the callback for swipe ,i.e, is the photo swiped right or swiped left :-

       photoSwipeView.setCallbackLambda { dir ->
            when (dir) {
                PhotoSwipeView.RIGHT -> { // swiped right
                }
                PhotoSwipeView.LEFT -> {  // swiped left
                }
            }
        }
