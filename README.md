# Quest Pages

This is an **uncompleted** standalone app for editing HQM quest files.

A lot of dialogs work. Icon rendering also works. You can create links, select multiple nodes, and move them around.

Make sure to download example_data.zip and extract it into this directory. If you don't, the demo won't run.

Main class to run for testing: com.sk89q.questpages.QuestPages.main()

If no icons show up, it's because you haven't added the proper icons in the right place. .png icons need to go into example_data/itempanel_icons as their name (i.e. "Yellow Stained Clay Slab.png"). This method has problems with conflciting item names but this was supposed to be temporary.

The quest is an uncompleted quest line from SKCraft.

## Status

* [x] Loading/saving JSON
* [ ] Loading/saving binary HQM
* [x] Item selection and search
* [x] Selecting multiple quests
* [x] Moving quests in bulk
* [x] Changing quest links
* [x] Changing quest availability
* [x] Changing quest visibility
* [x] Changing quest repeatability
* [x] Editing tasks
* [ ] Editing rewards
* [ ] Managing quest pages
* [ ] Managing reputations

## Screenshots

![](http://i.imgur.com/1zeOv5e.png)

![](http://i.imgur.com/YSdWbDA.png)

![](http://i.imgur.com/IHhnGXE.png)

![](http://i.imgur.com/xksynna.png)

![](http://i.imgur.com/Fk2yEPq.png)

![](http://i.imgur.com/H33qjQh.png)

## License

See LICENSE.txt.