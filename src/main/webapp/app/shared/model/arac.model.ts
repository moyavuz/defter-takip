import { Moment } from 'moment';

export const enum YakitTuru {
    DIZEL = 'DIZEL',
    BENZIN = 'BENZIN',
    BENZIN_LPG = 'BENZIN_LPG'
}

export interface IArac {
    id?: number;
    ad?: string;
    aciklama?: string;
    detay?: any;
    modelYili?: number;
    yakitTuru?: YakitTuru;
    tarih?: Moment;
    muayeneTarih?: Moment;
    kaskoTarih?: Moment;
    sigortaTarih?: Moment;
    bakimTarih?: Moment;
    resimContentType?: string;
    resim?: any;
    dosyaContentType?: string;
    dosya?: any;
}

export class Arac implements IArac {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public detay?: any,
        public modelYili?: number,
        public yakitTuru?: YakitTuru,
        public tarih?: Moment,
        public muayeneTarih?: Moment,
        public kaskoTarih?: Moment,
        public sigortaTarih?: Moment,
        public bakimTarih?: Moment,
        public resimContentType?: string,
        public resim?: any,
        public dosyaContentType?: string,
        public dosya?: any
    ) {}
}
