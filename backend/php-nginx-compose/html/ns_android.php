<?php
/*
 * 3.(stu_no)/(0~63)                students number
 * 4.(ans)/(1010101101010100.....)
 */
$save_info = 3;
$get_info = 4;
$mode = getRequest($_REQUEST['mode'], $student_homework_mode);
$stu_no = getRequest($_REQUEST['stu_no'], 1);
/**
 * $ans -> 11111111111111111111111111111111111111111111";
 */
$ans = $_REQUEST['ans'];
$file = "score/pgt";
$title_file = "score/pgt_title";
$member_file = "score/pgt_member";
$hw_len = 1;
$score = @file($file);
$title = @file($title_file);
$member = @file($member_file);
$MAXid = count($member);
$MAXhw = count($title);
if ($mode == $save_info) {
    {
        $fp = fopen($file . '.new', 'w');
        for ($i = 0; $i < $MAXid; $i = $i + 1)
        {
            $buf = '';
            for ($j = 0; $j < $MAXhw; $j = $j + 1)
            {
                if ($i == $stu_no) {
                    $tmp = (int) substr($ans, $j * $hw_len, $hw_len);
                    echo $tmp;
                } else {
                    $tmp = (int) substr($score[$i], $j * $hw_len, $hw_len);
                }
                $buf = $buf . sprintf("%0{$hw_len}d", $tmp);
            }
            fwrite($fp, $buf . "\n");
        }
        fclose($fp);
        rename($file . '.new', $file);
    }
} else if ($mode == $get_info) {
    for ($i = 0; $i < $MAXhw; $i++) {
        $tmp = (String) substr($score[$stu_no], $i, 1);
        if ($i == ($MAXhw - 1)) {
            echo $tmp;
        } else {
            echo $tmp . ',';
        }
    }
} else {
    echo 'no payload';
}

function getRequest(&$value, $default = null)
{
    return isset($value) ? $value : $default;
}
