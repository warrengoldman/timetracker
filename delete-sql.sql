delete FROM time_entry WHERE line_sk > 15000;
delete FROM batch_entry WHERE batch_sk = 15002;
delete FROM line_entry WHERE line_sk > 15000;