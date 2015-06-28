# FindItFirst
-------------

### What It Does
FindItFirst is a client-side application that is built on top of eBay's RESTful API utilizing the "findItemsAdvanced"
function. It automates eBay searches and sends email notifications anytime a new item result is discovered. In
addition, you can choose how often a search can run and even when to start and stop the search. The benefits speak
for themselves, but it's also worth noting that new items ALWAYS appear through eBay's API a significant amount of
time before they're listed on the actual site. This means you, quite literally, will "_FindItFirst_" :smile:

### How To Use It
In order to begin using this application you need to do three things:
* Get an eBay Developer Key. This can be acquired, for free,
[from eBay's dev site](https://go.developer.ebay.com/what-ebay-api).
You will then set ``finditfirst.ebay.API.EBAY_DEV_KEY_STRING`` to your developer key's AppID.
* You need to set the email and password for the email account
you'll be using to _send_ email notifications.
See ``finditfirst.utilities.MailService``
* Set the email to _receieve_ email notifications. This is done
using the Graphical User Interface, under settings.
__Note__: You can probably use the same address to send and receive
emails.

##### Logic
A custom ``JSplitPane`` is used, with a custom ``JTable`` (``SearchTable``) storing
(SearchEntry) objects, the equivalent of an ebay search, on the left
component of the pane. Anytime a ``SearchEntry`` is selected in the table,
an (``OptionPanel``) is loaded on the right component which contains
a number of optional filters for refining the selected search.
``SearchEntry`` contains the logic on how a search should be handled,
when it should run, and how often it should run.
