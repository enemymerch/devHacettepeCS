----main.m-----------
main script--> performs papari filter and color change


----color.m-------
Function that performs Color change from source image over target image

First argv is the path of the source image(color palette).
Second argv is the path of the target image.
Result image will be written as 'ColorChangeResult.jpg'

---------PapariFilter.m------------
Function that performs papari's filter on a given image

First argv is the path of source image
Second argv is the path of result image
3. argv is the sigma param of Papari's filter
4. argv is the Number of Sectors param of Papari's filter
5. argv is the q param of Papari's filter

----sector.m------
Function that creates needed sector over an image.

----smoothgaux.m-------
Function that perform a Gaussian filtering over an image


-----kuwahara.m-----------
Performs a Kuwahara filtering on a given image
Taken from this link "https://www.mathworks.com/matlabcentral/fileexchange/8171-kuwahara-filter"