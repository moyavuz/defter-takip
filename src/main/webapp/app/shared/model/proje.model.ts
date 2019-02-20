import { Moment } from 'moment';
import { IHakedis } from 'app/shared/model/hakedis.model';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { IBolge } from 'app/shared/model/bolge.model';

export const enum IsDurumu {
    BEKLIYOR = 'BEKLIYOR',
    BEKLIYOR_MALZEME = 'BEKLIYOR_MALZEME',
    BEKLIYOR_RUHSAT = 'BEKLIYOR_RUHSAT',
    CALISILIYOR = 'CALISILIYOR',
    TAMAMLANDI = 'TAMAMLANDI',
    TAMAMLANDI_EKSIK_VAR = 'TAMAMLANDI_EKSIK_VAR'
}

export interface IProje {
    id?: number;
    ad?: string;
    aciklama?: string;
    protokolNo?: number;
    durumu?: IsDurumu;
    tarih?: Moment;
    detay?: any;
    resimContentType?: string;
    resim?: any;
    dosyaContentType?: string;
    dosya?: any;
    baslamaTarihi?: Moment;
    bitisTarihi?: Moment;
    hakedis?: IHakedis[];
    turu?: IProjeTuru;
    bolge?: IBolge;
}

export class Proje implements IProje {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public protokolNo?: number,
        public durumu?: IsDurumu,
        public tarih?: Moment,
        public detay?: any,
        public resimContentType?: string,
        public resim?: any,
        public dosyaContentType?: string,
        public dosya?: any,
        public baslamaTarihi?: Moment,
        public bitisTarihi?: Moment,
        public hakedis?: IHakedis[],
        public turu?: IProjeTuru,
        public bolge?: IBolge
    ) {}
}
