# Wahenga
### Create your own family trees


It's strongly based on the java [Gedcom parser]

With _Wahenga_ you can:
- Create a family tree from scratch, entering names, dates, places, various events, photos and sources.
- Import an existing family tree through a Gedcom file and modify it as you want.
- Export the family tree you created (via Gedcom again) to import in every other genealogy program.
- Share a tree with your relatives, letting them improve it and receiving back the updates. Then you can choose whether accept them or not.

There are 2 modules:
- **app** is the actual _wahenga_ app.
- **lab** is the _Family Lab_ app, a playground used only to develop new features.

The code provided in this repository should compile and build a working version of _wahenga_, but with some limitations:
|Missing|Limitation|
|-|-|
|App signature|Loose saved trees when install over a signed version|
|Server account|Can't share trees|
|GeoNames "demo" account|Place names suggestions probably don't appear|
|Backup key|Android Backup Service is not available|

A little of the code (classes, variables, comments...) is written in italian, because of collaboration with an Italian Developer who is a friend.

Official website: https://wahenga.org

You can find _wahenga_ on [Google Play](https://play.google.com/store/apps/details?id=org.wahenga).

