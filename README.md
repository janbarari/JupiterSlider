# JupiterSlider
An Awesome Auto Slider for Android Developers

![](https://github.com/Studiomjt/JupiterSlider/blob/master/tablet.gif)
![](https://github.com/Studiomjt/JupiterSlider/blob/master/mobile.gif)

```gradle
dependencies {
	// ... other dependencies here
    compile 'com.dettaa:JupiterSlider:1.0'
}
```

See the Example Source Code

```java
ArrayList<Slide> slides = new ArrayList<>();
slides.add(new Slide(new ImageProvider("imageurl"), "uri", "#000000"));
slides.add(new Slide(new ImageProvider(BITMAP), "uri", "#000000"));
slides.add(new Slide(new ImageProvider(RESOURCEID), "uri", "#000000"));
slider.load(2, true,JupiterSlider.ScrollWays.Left, slides, 1200, 4000, this);	
```
