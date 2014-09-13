Simbiose Android Utils (SAU)
----------------------------

This a collection of utilities for android development, like Log Manager, Shared Preferences Management, SQLite management, Easy share, Key Key Map, Copy Assests easy, open documents with correct intent, decrypt & encrypt strings, Remote Image View and a lot of others littles things.

##License

Is a LGPL software, for more details see the LICENSE.md on root project folder.

##Developer

- Ademar Alves de Oliveira <ademar111190@gmail.com>

##About Simbiose

- <http://simbio.se>

##Contribute

Fell free to fork, modify and send a pull request.

##How to

Clone with recursive tag the sau repository and import the /android project named SAU to eclipse and add it a android library on your android application and now you're ready to use it.
You also can see the documentation on /doc/index.html

##Get It
- BitBucket <https://bitbucket.org/simbiose/sau/overview>
- GitHub <https://github.com/simbiose/SAU>

##Examples

###Log

    SimbiLog.print("Hello World"); //Print a normal String
    SimbiLog.print(param1, foo1, foo2); //You can print a lot of objects
    SimbiLog.printException(new Exception("A mock exception")); //Print Exceptions
    SimbiLog.log(this, param1, foo1, null, foo2); //You can log your methods
    SimbiLog.here(); //Easy "here" print

###Json

    Foo foo = (Foo) JsonUtils.fromJsonOrNull("{}", Foo.class); //You can load objects from json strings powered by GSON core
    JsonUtils.toJson(foo); //You can make json Strings from objects easily

###Shared Preferences

    PreferencesHelper myPrefs = new PreferencesHelper(getContext());
    myPrefs.put("Any Key", "Any Value"); //You can easily save data on shared preferences
    myPrefs.put("Any Key", new Foo()); //You can storage objects too
    myPrefs.getString("Any Key", "Default"); //Read is easy too
    myPrefs.getStringOrEmpty(keyString); //if you do not want a default values
    myPrefs.getObjectOrNull(keyObject, Foo.class); //Read objects

###Key Key Map

    KeyKeyMap keyKeyMap = new KeyKeyMap(); //Know the KeyKey Map
    
    String key1 = "Key 1"; //You can use any key, a String
	int key2 = 2; //any number
	Foo key3 = new Foo(); //or Objects too
	
	keyKeyMap.put(key1, key2); //Now you mapped key1 <-> key2
	keyKeyMap.get(key1); //returns the key2
	keyKeyMap.get(key2); //returns the key1
	keyKeyMap.get(key3); //returns null because you not mapped the key3
	
	keyKeyMap.put(key1, key3); //Now you mapped key1 <-> key3 and key1 <- key2
	keyKeyMap.get(key1); //returns the key3
	keyKeyMap.get(key3); //returns the key1
	keyKeyMap.get(key2); //returns the key1 too
	keyKeyMap.get(null); //returns null
	
	keyKeyMap.put(null, key3); //Now you mapped null <-> key3, key1 -> key3 and key1 <- key2
	keyKeyMap.get(null); //returns key3
	keyKeyMap.get(key3); //returns null
    
###Simple Encryption and Decryption of Strings

    Encryption encryption = new Encryption(); //The encryptor
    String key = "Some Key"; //The secret key to encrypt
	String data = "top secret string"; //The text to be encrypted
	String encrypted = encryption.encrypt(key, data); //Create the encrypted String
	SimbiLog.print(encrypted); //Just to you see
	String decrypted = encryption.decrypt(key, encrypted); //Back to original String
	SimbiLog.print(decrypted); //Just to you see again

###Range

    import static simbio.se.sau.iterable.Range.range; //It is like the range of python
    for (int i : range(3)) //passes through the numbers 0, 1 and 2
		SimbiLog.print("range example a", i);
	for (int i : range(-4)) //passes through the numbers 0, -1, -2 and -3
		SimbiLog.print("range example b", i);
	for (int i : range(4, 7)) //passes through the numbers 4, 5 and 6
		SimbiLog.print("range example c", i);
	for (int i : range(-5, -30, 10)) //passes through the numbers -5, -15 and -25
		SimbiLog.print("range example d", i);

###Range Seek Bar
####See the sample running to a better understanding of what the Range Seek Bar is

    //put on xml file
    <simbio.se.sau.view.RangeSeekBar
        android:id="@+id/range_seek_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_below="@+id/button_resize_my_width"
        android:layout_marginTop="@dimen/activity_vertical_margin"/>
    
    RangeSeekBar<Integer> rangeSeekBar = (RangeSeekBar<Integer>) findViewById(R.id.range_seek_bar); //load from xml
	rangeSeekBar.init( //init the params
	    0, //min value
	    100, //max value
	    BitmapFactory.decodeResource(getResources(), R.drawable.scrubber_control_normal_holo), //image to be the control BitmapFactory.decodeResource(getResources(), R.drawable.scrubber_control_pressed_holo), //image to be the controll pressed
	    this); //set listener and ends
	rangeSeekBar.setNormalizedMinValue(0.3f); //default min value
	rangeSeekBar.setNormalizedMaxValue(0.7f); //default max value
	rangeSeekBar.setNotifyWhileDragging(true); //call listener when drawing
	rangeSeekBar.setLineColor(Color.rgb(0, 153, 0)); //line color

####it will show something like that:
![Range Seek Bar][1]

###Clipboard Text View
####A TextView that when is clicked copy your text to clipboard

    //put on xml file
    <simbio.se.sau.view.ClipboardTextView
        android:id="@+id/clipboard_text_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_below="@+id/button_open_sql_example_activity"
        android:layout_marginTop="@dimen/activity_vertical_margin"
        android:text="@string/copy_me"
        android:textAppearance="?android:attr/textAppearanceLarge" />
    
    ClipboardTextView myCTV = (ClipboardTextView) findViewById(R.id.clipboard_text_view); //load from xml
    myCTV.setCopyListener(new CopiedInterface(){ //set interface
        @Override
	    public void textHasCopied(ClipboardTextView clipboardTextView, String stringCopied, boolean hasSucces) {
		    SimbiLog.print(stringCopied, hasSucces); //show the value
	    }
    });

###Remote Image View
####A remote image view tha make cache of downloaded image

    //Do not need java code
    <simbio.se.sau.view.RemoteImageView
        xmlns:sau="http://schemas.android.com/apk/res-auto"
        android:id="@+id/remote_imageview_working"
        android:layout_width="@dimen/zero"
        android:layout_height="match_parent"
        android:layout_weight="1"
        sau:defaultImage="@drawable/remote_image_default"
        sau:errorImage="@drawable/remote_image_fail"
        sau:imageUrl="https://any.png" />
    
    //But you can use java code too
    RemoteImageView remoteImageView = new RemoteImageView(context); //create a view or load from xml if prefers
    remoteImageView.setImageUrl("https://any.png"); //any image url
    remoteImageView.setErrorImage(R.drawable.remote_image_fail); //a local image resource to be used when download fails
	remoteImageView.setDefaultImage(R.drawable.remote_image_default); //a local image resource to be used when download is not ended
	remoteImageView.deleteCachedImage(); //Maybe you wants delete the cached image
	remoteImageView.start(); //Starts download proccess

###Share

    SimbiShare.shareWithChooser(this, R.string.hello_world, R.string.app_name, R.string.action_share); //an easy way to share with android native choose method
    SimbiShare.shareWithChooser(this, "content", "subject", "title"); //you can use Strings too

###Toast maker

    ToastMaker.toast(getApplicationContext(), "Some Text"); //show toasts easily
    ToastMaker.toast(getApplicationContext(), R.string.hello_world); //use strings xml resources too
    ToastMaker.toast(getApplicationContext()); // Toast just a "here"

###Resize Animation

    ResizeAnimation resizeAnimation = new ResizeAnimation(view, toHeight, toWidth); //Resive the view to new height and width
    resizeAnimation.setDuration(resizeDuration); //set the duration of animation
	resizeAnimation.setAnimationListener(this); //set the listener if you want
	view.startAnimation(resizeAnimation); //start it

###SQLite
####powered by [Shiva][2]

    //To use SQLite easily extends our class DatabaseHelper like bellow
    public class SqlManager extends DatabaseHelper {
	    public SqlManager(Context context, DatabaseDelegate delegate) {
		    super(context, delegate, "yourDatabase.sqlite", 1);
	    }
	    @Override
	    protected List<Class<?>> getModelsToBeCreatedOnDatabase() {
	        //create this list to add the classes that you want save on database
		    List<Class<?>> clazzez = new ArrayList<Class<?>>();
		    //add any classes that you want
		    clazzez.add(SqlFooModel.class);
		    return clazzez;
	    }
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        //normal onUpgrade of android sqlite
	    }
    }
    
    //before we use, we need create a delegate. You can implements DatabaseDelegate interface on your own class too
    DatabaseDelegate delegate = new DatabaseDelegate() {
		@Override
		public void onRequestSuccess(DatabaseHelper databaseHelper, int requestId, Object object) {
		    //do anything that you want, the object is the return os a select for example
		}
		@Override
		public void onRequestFail(DatabaseHelper databaseHelper, int requestId, Exception exception) {
		    //if fails
		}
	};
	
	//now we can use our sqlite easily
	new SqlManager(getApplicationContext(), this).insert(new SqlFooModel(), REQUEST_ADD); //insert
	new SqlManager(getApplicationContext(), delegate).select(SqlFooModel.class, REQUEST_LOAD); //select
    new SqlManager(getApplicationContext(), this).delete(object, REQUEST_DELETE); //delete
    new SqlManager(getApplicationContext(), this).clearTable(REQUEST_DELETE_ALL, SqlFooModel.class); //clear table

###Open Documents

    new ActivityUtils(this).openDocumentWithCorrectActivity("/path/to/text.txt"); //open the txt file with correct intent
    new ActivityUtils(this).openDocumentWithCorrectActivity("/path/to/doc.ods"); //works to any file format since the android device has the correctly app to open this file format
    new ActivityUtils(this).openDocumentWithCorrectActivity("/path/to/music.ogg"); //just more one example

###Array Adapter
####to be used on list views or fragments adapters

    //first extends the class AbstractAdapter and send the type of object used by your adapter
    public class MyAdapter extends AbstractAdapter<FooModel> {
        public interface MyAdapterDelegate extends IAbstractAdapter<FooModel> {
	    }
        public SqlListViewAdapter(Context context, MyAdapterDelegate delegate) {
		    super(context, delegate, R.layout.your_xml_layout);
	    }
    }
    
    //it's all but probably you want edit your views so you can override getView method like:
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rootView = super.getView(position, convertView, parent);
		// do anything with your rootView, to get views inside use rootView.findViewById(id);
    	return rootView;
	}

###Copy files from assest to an usable folder

    ActivityUtils activityUtils = new ActivityUtils(this);
    // we need files on sdcard, so we go copy from assests to a folder called sauSample on sdcard
    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sauSample/";
    try {
        activityUtils.copyAssetsFilesToFolder(path);
    } catch (Exception e) {
        ToastMaker.toast(getApplicationContext(), e);
    }

###Get primary user email

    //you need the follow permission:
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    
    //and you need the follow code
    DeviceInformationsManager dim = new DeviceInformationsManager(context)
    String email = dim.getPrimaryUserEmailOrNull()

###Null or Empty validation

    NullOrEmpty.verify(""); //returns true
    NullOrEmpty.verify("not empty"); //returns false
    NullOrEmpty.verify(null); //returns true
    ArrayList<Object> list = null;
    NullOrEmpty.verify(list); //returns true
    list = new ArrayList<Object>();
    NullOrEmpty.verify(list); //returns true
    list.add(new Foo());
    NullOrEmpty.verify(list); //returns false

####SAU have a lot of more things, see the examples :D


  [1]: https://raw2.github.com/simbiose/SAU/master/images/rangeSeekBar.png
  [2]: https://github.com/simbiose/Shiva

