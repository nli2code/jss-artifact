# How to use our plugins to write code in MPS

## index

1. Install plugins in MPS
2. Some initialization before using plugin in a new project
3. An example of using our plugin to write code in MPS

## Install plugin in MPS

### for mac os
1. open MPS
2. click `MPS`——>`Preference` on the upper-left corner
3. select `Plugins` in the popup
4. click `Install plugin from disk` on the lower-right corner
5. select `JavaPoiExtention.zip` in the popup
6. click `Restart Jetbrains MPS`，the plugin will be available after the restart

### for windows

1. open MPS
2. click `File`——>`Settings` on the upper-left corner
3. select `Plugins` in the popup
4. click the gear on the upper-right corner, select `Install Plugin from Disk` in the popup
5. select `JavaPoiExtention.zip` in the popup
6. click `Restart IDE`，the plugin will be available after the restart

## Some initialization before using plugin in a new project

1. open MPS

2. click `Create New Project`

3. select `Solution Project` on the left hand，fill in `Project Name` and `Solution Name` on the right hand，click`OK`

4. create a new model in the solution

    ![](./pictures/installation/1.png)

5. fill in the name of the model, click `OK`

    ![](./pictures/installation/2.png)

6. set the `dependencies` and `used languwages` of the model in the popup (here we use JavaPpoiExtension as an example. for the `dependencies` and `used languwages` of other plugin, please go to the corresponding plugin folder and read the plugin-specific manual)

7. add dependency `org.apache.poi.ss.usermodel`

    ![](./pictures/installation/3.png)

8. add dependency `org.apache.poi.hssf.usermodel`

    ![](./pictures/installation/4.png)

9. add languwage `POI`

    ![](./pictures/installation/5.png)

10. add languwage `jetbrains.mps.baselanguage`

    ![](./pictures/installation/6.png)



## An example of using our plugin to write code in MPS

Here we use JavaPpoiExtension as an example.

### Some keyboard shortcut

these shortcuts can be modified through `MPS`-->`Preference`-->`keymap`

1. `complete smart`：we use`alt`+`space`
2. `intentions`：list all intention functions，it can't be changed，we use `alt`+`enter`
3. `Expand Selection`：expand selection on the AST, we use `command`+`up`
4. `Shrink Selection`：shrink selection on the AST, we use `command`+`down`
5. `Select Previous`：select previous line, we use `shift`+`up`
6. `Select Next` ：select next line, we use `shift`+`down`
7. `copy & paste`：we use `ctrl`+`c`，`ctrl`+`v`

### An example

1. create a java class in the model

    ![](./pictures/example/0.png)

2. after filling in the class name and main function(psvm), type in `crea` and press `ctrl`+`space`, then pick `CreateNewHSSFWorkbook` to create a new workbook

    ![](./pictures/example/1.png)

3. fill the empty cells(just like what we do in the previous step) 

    ![](./pictures/example/2.png)

4. create a sheet in our workbook

    ![](./pictures/example/3.png)

5. create some cells in our sheet

    ![](./pictures/example/4.png)

6. the range of the cell block is from (0,0) to (10,10)

    ![](./pictures/example/5.png)

7. the cell type is `CellType.NUMERIC`，and set it's value to 2

    ![](./pictures/example/6.png)

8. iterate a sheet, including it's empty rows and empty cells

    ![](./pictures/example/7.png)

9. fill in some processing statements in corresponding block

    ![](./pictures/example/8.png)

10. deal with each nonempty cell according to their types

    ![](./pictures/example/9.png)

11. print cell content, if it's a `NUMERIC` cell, set it's value to 1

    ![](./pictures/example/10.png)

12. set cell color

    ![](./pictures/example/11.png)

13. set foreground color to AQUA and background color to RED, and the fill pattern to BIG_SPOTS

    ![](./pictures/example/12.png)

14. set cell font

    ![](./pictures/example/13.png)

15. name it as `Courier New`，and set some properties

    ![](./pictures/example/14.png)

16. get the content of the workbook

    ![](./pictures/example/15.png)

17. print the content of the workbook

    ![](./pictures/example/16.png)

18. run the main function of the class

    ![](./pictures/example/17.png)

19. some outputs

    ![](./pictures/example/18.png)

20. right click in the editor, select `Preview Generated Text`

    ![](./pictures/example/19.png)

21. part of the automatically generated java code

    ![](./pictures/example/20.png)
    
### how to set the working directory：

1. open `Run` menu，select `Edit Configurations`

    ![](./pictures/working_directory/1.png)
    
2. edit `Working directory`

    ![](./pictures/working_directory/2.png)
 

## This Complete the Tutorial
