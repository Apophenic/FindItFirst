# FindItFirst
-------------
![Sample Img](https://github.com/Apophenic/FindItFirst/blob/master/res/sample.jpg)
_FindItFirst_ is a client-side application that is built on top of eBay's RESTful API for automating eBay searches.

### What It Does
----------------
_FindItFirst_ offers the ability to create highly customizable automated eBay searches.
After creating a search and scheduling when it should run and how frequently, _FindItFirst_ will send you email
notifications anytime a new item result is discovered.

If you're an eBay power user and need to see new listings immediately as they appear on the site, _FindItFirst_ will
be a great asset. New items are _always_ accessible through eBay's API a significant amount of
time before they're listed on the actual site. This means you, quite literally, will "_FindItFirst_" :smile:

### How To Use It
-----------------
In order to begin using this application you need to do three things:
* Get an eBay Developer Key. This can be acquired, for free,
[from eBay's dev site](https://go.developer.ebay.com/what-ebay-api).
* Edit the _api-settings.txt_ file to include:
    * Your eBay Developer key
    * The email address that will send notifications
    * The email address' password
* Using the settings dialog in _FindItFirst_'s UI, add the email you'd like to use for receiving notifications.
