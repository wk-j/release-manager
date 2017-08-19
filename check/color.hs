-- stack runghc --package colour

import Control.Monad (forM_)
import System.Console.ANSI
import Data.Colour.SRGB (sRGB)
import Data.Colour.RGBSpace (uncurryRGB )
import Data.Colour.RGBSpace.HSL (hsl)

main = do
    forM_ [0..2*360] $ \hue2 -> do
        let color = uncurryRGB sRGB $ hsl (hue2/2) 1 0.5
        setSGR [SetRGBColor Background color]
        putStr " "
    setSGR [Reset]
    putStrLn " "
