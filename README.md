# :scroll: ROI Extraction in Thermographic Breast Images Using Genetic Algorithm :scroll:

### :dart: Work:

This work was created by Lucas C. Mendes, Erick O. Rodrigues, Sandro C. Izidoro, Aura Conci and Panos Liatsis and published in the 27th International conference on Systems, Signals and Image Processing (IWSSIP)

###### doi: 10.1109/IWSSIP48289.2020.9145346.

### :clipboard: Abstract:

This work proposes the use of Genetic Algorithms (GA) to identify the area of the breast from the background in thermographic breast images. The proposed method uses color information, a fitness function based on cardioids, and GA. This is the first work in the literature to propose a Region of Interest (ROI) extraction based on GA and cariods. ROI extraction can improve the accuracy of cancer detection and assist with the standardization of acquisition protocols. The method is able to successfully separate the breast region in 52 out of 58 images, while being fully automatic, and not requiring manual selection of seed points.

### :books: Results

This work proposes a ROI extraction methodology for ther-mographic  breast  images  based  on  genetic  algorithms.  Weexploit  the  shape  of  cardioids  jointly  with  gray  level  data  todefine a fitness function that is evaluated by a genetic algorithmover  time.  Our  method  does  not  require  manual  placement  ofseed  points  and  is  therefore  entirely  automatic,  in  contrast  toother works in the literature .

The  proposed  algorithm  was  able  to  provide  perfect  ROIextraction  in  16  out  of  58  images  and  satisfactory  results  ina  further  36  cases.  In  the  case  of  perfect  ROI  extraction,  weconsider  a  tight  and  correct  ROI  extraction  with  no  exclusionof critical breast parts. We consider instances of minor or verylittle  exclusion  of  critical  parts  in  the  breasts  as  satisfactory results. The remaining 6 cases provided poor results, where thecardiod  ended  up  being  displaced  over  the  arm  of  the  patientor another unrelated area. 

In  terms  of  time  evolution,  the  GA  showed  better  resultsafter  50  generations,  requiring  approximately  60  seconds  toidentify the ROI. Results tend to be better with healthy womenas  gray  level  information  is  more  homogeneous.  In  contrast,sick women (mainly those who have been sick for a long timeand with more severe instances of disease) present substantiallyless homogeneous gray level intensity patterns

### :file_folder: Images
<div>
<img width='30%' src='https://user-images.githubusercontent.com/26746227/88820819-32753e00-d198-11ea-974d-cbd309c3d3b4.jpg'/>
<img width='30%' src='https://user-images.githubusercontent.com/26746227/88820844-3b660f80-d198-11ea-8dd0-7bb995d97d0f.jpg' />
<img width='30%' src='https://user-images.githubusercontent.com/26746227/88820852-3c973c80-d198-11ea-94ee-c52c3c064e2f.jpg' />
</div>

