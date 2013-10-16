Project: Holo Acceent
Developer: Borja Lopez Urkidi
Organization: NEGU Soft
Web: http://www.negusoft.com


Description
===========

Android library project to customize the Holo theme with a custom accent color.

It currently supports Android 3.0+ projects (for the moment). Each theme defined in the project corresponds to a default android theme:
- Theme.Holo > Theme.HoloAccent
- Theme.Holo.Light > Theme.HoloAccent.Light
- Theme.Holo.Light.DarkActionBar > Theme.HoloAccent.Light.DarkActionBar

The library is still in an early development stage. 


Usage
=====

To make use of this project follow these steps:

 1 - Inport the HoloAccent project in your workspace and reference it as a "library project".
 (on eclipse: [project properties] > Andriod > Add...)

 2 - Apply the HoloAccent theme on your projets AndroidManifest.xml for every activity you want to customize. Or you can apply it for the whole application.

 3 - Add the following code to each activity you want to apply the style to:
	private final ResourceHelper mResourceHelper = new ResourceHelper();
	@Override
	public Resources getResources() {
		return mResourceHelper.getResources(this, super.getResources());
	}
Dont' forget to add the corresponding imports:
	import com.negusoft.holoaccent.ResourceHelper;
	import android.content.res.Resources;

 *Have a look at HoloAccentExample as a reference to how use HoloAccent.


License
=======

   Copyright 2013 NEGU Soft

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.


Implementation Details
======================

HoloAccent makes user of several techniques to be able to customize the accent color:

- Themes & styles:
This is simply the default way to customize your android app. The HoloAccent themes extend the default android themes and customize the properties to change the accent color.

- XML drawables:
As long as possible, the drawables were defined in XML, instead of using bitmpas like PNG files. "layer-list" and "shape" objets have been used mostly to define the drawables. It is not possible to use this technic for more complex drawables (like text fields or check boxes).

- Drawable replacement on runtime:
The "Resources" class is extended to override some behavior. Like this, bitmaps can be modified on runtime to apply the accent color and then replace them.

- Split up drawables:
In some cases, the image has to be split up. For example, the checkbox is divided into two layers. The accent color layer that will be modified on runtime and the details that will be drawn over. Also, 9-patch images can't be overriden on runtime so textview backgrounds are divided into three parts: left, right and center(stretched).


Coming Up Next...
=================

First of all, the project is in developments. There are still some UI components missing (like progressbar and seekbar). More themes have to be added, like dialogs and other default Holo themes. Then it has to be supported by solid examples that show all the functionality.

Next, the objective is to support older versions of android. For this purpose, the idea is to extend the project to support ActionBarSherlock and ActionBarCompat.

And finally, a lot of testing, documentation... still a lot of work to do.
